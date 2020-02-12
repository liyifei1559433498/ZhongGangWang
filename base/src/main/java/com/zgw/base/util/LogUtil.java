package com.zgw.base.util;

import android.util.Log;

public class LogUtil {

    private static String CLASSNAME = "ZGWLog";

    private static String METHODNAME = "ZGWLog";

    public static void myOutLog(String tag, String msg) {
        outLog(tag, msg);
    }

    /**
     * 输出信息
     *
     * @param className
     * @param methodName
     */
    public static void outLog(String className, String methodName) {
        if (!Constants.isDebug)
            return;
        Log.e(Constants.TAG, CLASSNAME + " = " + className + "; " + METHODNAME
                + " = " + methodName);
    }
    /**
     * 输出信息
     *
     * @param className
     * @param methodName
     */
    public static void outLog(Object className, String methodName) {
        // 调试状态屏蔽log
        if (!Constants.isDebug)
            return;
        Log.e(Constants.TAG, CLASSNAME + " = " + className.getClass().getSimpleName() + "; " + METHODNAME
                + " = " + methodName);
    }
}
