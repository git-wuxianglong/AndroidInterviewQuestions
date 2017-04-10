package com.wuxl.interviewquestions.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author:wuxianglong;
 * Time:2017/2/28.
 */
public class AppUtils {

    /**
     * 获取VersionName
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            return context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据包名判断手机上是否安装了此app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断网络是否有效.
     *
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
