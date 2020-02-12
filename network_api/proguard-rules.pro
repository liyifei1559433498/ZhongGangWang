# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                                                  # 混淆时是否做预校验
-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法
-dontskipnonpubliclibraryclassmembers

-dontshrink
-dontoptimize

-flattenpackagehierarchy
-allowaccessmodification
-printmapping mapping.txt
#-applymapping mapping.txt
-ignorewarnings
-keepattributes Exceptions,InnerClasses
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keepattributes Signature
-keepattributes *Annotation*

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}

-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends roboguice.activity.RoboActivity
-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class roboguice.** { *; }
-keep class com.google.inject.** { *; }

-keep class edu.umd.cs.findbugs.annotations.** { *; }
-keep class org.roboguice.shaded.goole.common.** { *; }
-keepclassmembers class * {
    @com.google.inject.Inject <fields>;
    @com.google.inject.Inject <init>(...);
}
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }

-keep class com.google.inject.Binder


-keepclassmembers class * {
    void *(**On*Event);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { *; }

-keep class * implements java.io.Serializable { *; }

-keep public class * extends android.view.View{ *; }

-keep public class * extends android.view.View

-keepclassmembers class * {
    <init>();
}

 -dontwarn roboguice.**
    -keep class roboguice.** {
    *;
 }
  -dontwarn com.alibaba.fastjson.**
    -keep class com.alibaba.fastjson.** {
    *;
 }

 -dontwarn javax.inject.**
     -keep class javax.inject.** {
     *;
  }

  #PictureSelector 2.0
  -keep class com.luck.picture.lib.** { *; }

  -dontwarn com.yalantis.ucrop**
  -keep class com.yalantis.ucrop** { *; }
  -keep interface com.yalantis.ucrop** { *; }

   #rxjava
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

  #rxandroid
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

  #glide
  -keep public class * implements com.bumptech.glide.module.GlideModule
  -keep public class * extends com.bumptech.glide.AppGlideModule
  -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
  }

  -dontwarn com.tencent.bugly.**
  -keep public class com.tencent.bugly.**{*;}
  -keep class android.support.**{*;}

  -dontwarn com.umeng.**
  -dontwarn com.taobao.**
  -dontwarn anet.channel.**
  -dontwarn anetwork.channel.**
  -dontwarn org.android.**
  -dontwarn org.apache.thrift.**
  -dontwarn com.xiaomi.**
  -dontwarn com.huawei.**
  -dontwarn com.meizu.**

  -keepattributes *Annotation*

  -keep class com.taobao.** {*;}
  -keep class org.android.** {*;}
  -keep class anet.channel.** {*;}
  -keep class com.umeng.** {*;}
  -keep class com.xiaomi.** {*;}
  -keep class com.huawei.** {*;}
  -keep class com.meizu.** {*;}
  -keep class org.apache.thrift.** {*;}

  -keep class com.alibaba.sdk.android.**{*;}
  -keep class com.ut.**{*;}
  -keep class com.ta.**{*;}

  -keep public class **.R$*{
     public static final int *;
  }