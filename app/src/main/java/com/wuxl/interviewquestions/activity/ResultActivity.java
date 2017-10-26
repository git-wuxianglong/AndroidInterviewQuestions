package com.wuxl.interviewquestions.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.wuxl.interviewquestions.R;
import com.wuxl.interviewquestions.bean.AndroidSenior;
import com.wuxl.interviewquestions.bean.JavaQuestions;
import com.wuxl.interviewquestions.bean.JavaWebQuestions;
import com.wuxl.interviewquestions.bean.Subject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.wuxl.interviewquestions.AppConfig.ANDROID_BASE_FLAG;
import static com.wuxl.interviewquestions.AppConfig.ANDROID_SENIOR_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_WEB_FLAG;

/**
 * 问题答案列表
 * Created by wuxianglong on 2016/9/8.
 */
public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.text_ask)
    TextView textAsk;
    @Bind(R.id.text_answer)
    TextView textAnswer;

    private Subject question;//android基础
    private JavaQuestions mJavaQuestions;//Java
    private AndroidSenior mAndroidSenior;//Android高级
    private JavaWebQuestions mJavaWebQuestions;//Java Web

    private String flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        //页面过度动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
                getWindow().setExitTransition(slide);
                getWindow().setEnterTransition(slide);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Intent it = getIntent();
        if (!TextUtils.isEmpty(it.getStringExtra("flag")) && it.getStringExtra("flag") != null) {
            flag = it.getStringExtra("flag");
        }

        int position = it.getIntExtra("position", 0);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && flag.equals(JAVA_FLAG)) {
            //Java面试题
            actionBar.setTitle(R.string.java_questions);
            mJavaQuestions = (JavaQuestions) it.getSerializableExtra("question");
            textAsk.setText(position + "：" + mJavaQuestions.getQuestions());
            textAnswer.setText(mJavaQuestions.getAnswer());

        } else if (actionBar != null && flag.equals(ANDROID_BASE_FLAG)) {
            //Android基础
            actionBar.setTitle(R.string.android_base_questions);
            question = (Subject) it.getSerializableExtra("question");
            textAsk.setText(position + "：" + question.getSubject_title());
            textAnswer.setText(question.getSubject_answer());

        } else if (actionBar != null && flag.equals(ANDROID_SENIOR_FLAG)) {
            //Android高级
            actionBar.setTitle(R.string.android_senior_questions);
            mAndroidSenior = (AndroidSenior) it.getSerializableExtra("question");
            textAsk.setText(position + "：" + mAndroidSenior.getQuestions());
            textAnswer.setText(mAndroidSenior.getAnswer());
        } else if (actionBar != null && flag.equals(JAVA_WEB_FLAG)) {
            //Java Web
            actionBar.setTitle(R.string.java_web_questions);
            mJavaWebQuestions = (JavaWebQuestions) it.getSerializableExtra("question");
            textAsk.setText(position + "：" + mJavaWebQuestions.getQuestions());
            textAnswer.setText(mJavaWebQuestions.getAnswer());
        }

        if (actionBar != null) {
            //返回键
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //启动动画
        textAsk.post(new Runnable() {
            @Override
            public void run() {
                startTextAnim(textAsk);
            }
        });

        textAnswer.post(new Runnable() {
            @Override
            public void run() {
                startTextAnim(textAnswer);
            }
        });

    }

    /**
     * TextView展开动画（5.0以上）
     *
     * @param view
     */
    private void startTextAnim(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(
                    view, 0, 0, 0,
                    (float) Math.hypot(view.getWidth(), view.getHeight()));
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(700);
            animator.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //返回
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                finishAfterTransition();
            else
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            finishAfterTransition();
    }
}
