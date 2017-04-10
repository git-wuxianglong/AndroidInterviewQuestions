package com.wuxl.interviewquestions.activity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wuxl.interviewquestions.R;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 * Created by wuxianglong on 2016/10/10.
 */

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_java)
    LinearLayout btnJava;
    @Bind(R.id.btn_android_base)
    LinearLayout btnAndroidBase;
    @Bind(R.id.btn_android_senior)
    LinearLayout btnAndroidSenior;
    @Bind(R.id.img_java)
    ImageView imgJava;
    @Bind(R.id.img_android_base)
    ImageView imgAndroidBase;
    @Bind(R.id.img_android_senior)
    ImageView imgAndroidSenior;

    private Intent intent = new Intent();

    private static final String JAVA_FLAG = "java";//java题
    private static final String ANDROID_BASE_FLAG = "android_base";//android基础题
    private static final String ANDROID_SENIOR_FLAG = "android_senior";//android高级题

    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeActionOverflowMenuShown();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            startAnimator(imgJava);
            startAnimator(imgAndroidBase);
            startAnimator(imgAndroidSenior);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private long pre_press_time = 0;
    private static final long INTERVAL_TIME = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long cur_time = System.currentTimeMillis();
            if (cur_time - pre_press_time > INTERVAL_TIME) {
                Snackbar.make(btnJava, getString(R.string.exit_app_hint), Snackbar.LENGTH_SHORT).show();
                pre_press_time = cur_time;
                return false;
            }
            // 退出app
            MainActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 有的手机不显示菜单栏
     */
    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.btn_java, R.id.btn_android_base, R.id.btn_android_senior})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_java:
                toQuestionsActivity(JAVA_FLAG);
                break;
            case R.id.btn_android_base:
                toQuestionsActivity(ANDROID_BASE_FLAG);
                break;
            case R.id.btn_android_senior:
                toQuestionsActivity(ANDROID_SENIOR_FLAG);
                break;
        }
    }

    /**
     * 跳转到问题列表界面
     *
     * @param flag
     */
    private void toQuestionsActivity(String flag) {
        if (!TextUtils.isEmpty(flag)) {
            intent.putExtra("flag", flag);
            intent.setClass(MainActivity.this, QuestionListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 启动动画
     *
     * @param imageView
     */
    private void startAnimator(ImageView imageView) {
        drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
