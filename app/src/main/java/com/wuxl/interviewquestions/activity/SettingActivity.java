package com.wuxl.interviewquestions.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.wuxl.interviewquestions.AppConfig;
import com.wuxl.interviewquestions.R;
import com.wuxl.interviewquestions.utils.CacheUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.is_show_javaweb)
    Switch isShowJavaweb;

    private String CACHE_DIR = null;//默认缓存目录
    private boolean showJavaWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.action_setting));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        CACHE_DIR = this.getCacheDir().getPath();
        //读取配置信息
        showJavaWeb = (boolean) CacheUtils.load(CACHE_DIR + AppConfig.SETTING_DIR);
        isShowJavaweb.setChecked(showJavaWeb);

        isShowJavaweb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                CacheUtils.save(isChecked, CACHE_DIR + AppConfig.SETTING_DIR);
            }
        });
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
