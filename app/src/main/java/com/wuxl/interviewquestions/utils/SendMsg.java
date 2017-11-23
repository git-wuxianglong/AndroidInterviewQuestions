package com.wuxl.interviewquestions.utils;

import android.content.Context;

import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONObject;

/**
 * 发送用户行为
 * Author:wuxianglong;
 * Time:2017/10/27.
 */
public class SendMsg {

    /**
     * 发送用户操作行为到诸葛io
     *
     * @param context context
     * @param tabName 点击的控件名称
     */
    public static void sendMsgToZhuge(Context context, String tabName) {

        try {
            //定义与事件相关的属性信息
            JSONObject eventObject = new JSONObject();
            eventObject.put("点击", tabName);
            //记录事件,以购买为例
            ZhugeSDK.getInstance().track(context, "用户点击操作", eventObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
