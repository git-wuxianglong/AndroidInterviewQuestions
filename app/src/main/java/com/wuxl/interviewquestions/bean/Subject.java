package com.wuxl.interviewquestions.bean;

import java.io.Serializable;

/**
 * Android问题和回答bean
 * （类名和变量名需要跟数据库保持一致！）
 * Created by wuxianglong on 2016/9/8.
 */
public class Subject implements Serializable {
    private String subject_title;
    private String subject_answer;
    private String createdAt;

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public String getSubject_answer() {
        return subject_answer;
    }

    public void setSubject_answer(String subject_answer) {
        this.subject_answer = subject_answer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subject_title='" + subject_title + '\'' +
                ", subject_answer='" + subject_answer + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
