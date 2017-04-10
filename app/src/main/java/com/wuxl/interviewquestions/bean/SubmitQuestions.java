package com.wuxl.interviewquestions.bean;

import cn.bmob.v3.BmobObject;

/**
 * 提交遇到的面试题app问题/bean
 * （类名和变量名需要跟数据库保持一致！）
 * Created by wuxianglong on 2016/9/12.
 */
public class SubmitQuestions extends BmobObject {
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
