# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-ignorewarnings

-keepattributes Signature,*Annotation*

# keep BmobSDK
-dontwarn cn.bmob.v3.**
-keep class cn.bmob.v3.** {*;}

# keep Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#不需要混淆的类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.widget.BaseAdapter {*;}
-keepnames class * implements java.io.Serializable

# 确保JavaBean不被混淆-否则gson将无法将数据解析成具体对象
-keep class com.wuxl.interviewquestions.bean.**{*;}
-keep class com.wuxl.interviewquestions.bean.Subject{*;}
-keep class com.wuxl.interviewquestions.bean.SubmitQuestions{*;}

# keep okhttp3、okio
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-keep interface okhttp3.** { *; }
-dontwarn okio.**
# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

# keep rx
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# butterknife
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# 如果你需要兼容6.0系统，请不要混淆org.apache.http.legacy.jar
-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.http.**{*;}
