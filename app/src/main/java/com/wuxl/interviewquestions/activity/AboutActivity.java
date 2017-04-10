package com.wuxl.interviewquestions.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuxl.interviewquestions.R;
import com.wuxl.interviewquestions.bean.SubmitQuestions;
import com.wuxl.interviewquestions.utils.AppUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 关于界面
 * Created by wuxianglong on 2016/9/9.
 */
public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.et_question)
    EditText etQuestion;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.alipay)
    LinearLayout alipay;
    @Bind(R.id.versionName)
    TextView versionName;
    @Bind(R.id.versionDescription)
    LinearLayout versionDescription;

    private Snackbar snackbar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        versionName.setText(AppUtils.getAppVersionName(this));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_about);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick({R.id.submit, R.id.versionDescription, R.id.alipay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.versionDescription:
                //版本更新说明
                final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
                dialog.setTitle("版本更新说明");
                dialog.setMessage("1：修复了一个bug\n\n2：增加了简单的自动缓存，列表有多少条数据就缓存多少条，直接覆盖存储，不会有数据冗余。由于时间匆忙，没怎么测试\n\n3：下个版本增加清除缓存吧，最近有点忙，感谢大家的反馈");
                dialog.setPositiveButton("确定", null);
                dialog.show();
                break;
            case R.id.submit:
                //提交
                String question = etQuestion.getText().toString().trim();
                if (!TextUtils.isEmpty(question)) {
                    SubmitQuestions sq = new SubmitQuestions();
                    sq.setQuestion(question);
                    sq.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                //添加成功
                                showSnackBar(submit, "提交成功");
                                //清空
                                etQuestion.setText("");
                            } else {
                                //失败
                                showSnackBar(submit, "提交失败");
                            }
                        }
                    });
                } else {
                    showSnackBar(view, "还没填写问题");
                }
                break;
            case R.id.alipay:
                //支付宝捐赠
                if (AppUtils.isApkInstalled(this, "com.eg.android.AlipayGphone")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    String alipayUrl = "HTTPS://QR.ALIPAY.COM/FKX00724CT3ZMWDTHWNA26";
                    intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + alipayUrl));
                    if (intent.resolveActivity(this.getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        intent.setData(Uri.parse(alipayUrl));
                        startActivity(intent);
                    }
                } else {
                    showSnackBar(alipay, "手机上未安装支付宝！");
                }
                break;
        }
    }


    /**
     * SnackBar
     *
     * @param view
     * @param text
     */
    private void showSnackBar(View view, String text) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        } else {
            snackbar.setText(text);
            snackbar.setDuration(Snackbar.LENGTH_SHORT);
        }
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
