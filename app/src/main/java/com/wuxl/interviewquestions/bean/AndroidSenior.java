package com.wuxl.interviewquestions.bean;

import java.io.Serializable;

/**
 * Android高级面试题 bean
 * （类名和变量名需要跟数据库保持一致！）
 * Created by wuxianglong on 2016/10/12.
 */
public class AndroidSenior implements Serializable {
    private String questions;
    private String answer;
    private String createdAt;

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
