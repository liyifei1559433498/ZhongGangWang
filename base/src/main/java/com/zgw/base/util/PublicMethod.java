package com.zgw.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PublicMethod {


    private static long lastClickTime;



    public static boolean isMobileNet(Context context){
        boolean connectGPRS = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager
                    .getActiveNetworkInfo();
            try {
                if (activeNetInfo != null) {
                    if (connectivityManager.getNetworkInfo(
                            ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
                        connectGPRS = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connectGPRS;
    }

    /**
     * 拼接需要传递给h5参数的方法
     */
    public String splitH5Parameter(String type, String netState) {
        org.json.JSONObject jsonProtocol = new org.json.JSONObject();
        try {
            jsonProtocol.put("type", type);
            jsonProtocol.put("netState", netState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonProtocol.toString();
    }


    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    public static boolean checkWirelessNetwork(Context context){
        boolean connectWIFI = false;
        boolean connectGPRS = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager
                    .getActiveNetworkInfo();
            try {
                if (activeNetInfo != null) {
                    if (connectivityManager.getNetworkInfo(
                            ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        connectWIFI = true;
                    }
                    if (connectivityManager.getNetworkInfo(
                            ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
                        connectGPRS = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connectWIFI || connectGPRS;
    }

    public static boolean isAppRunning(Context context) {
        String packageName = getPackageName(context);
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }
    /**
     * 获取产品标识 用于后台统计
     * @return
     */
    public static String getProductName(Context context) {
//        try {
//            String packageName = context.getPackageName();
//            if ("com.quanmincai.bizhong".equals(packageName)) {
//                return "bzcp";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "jzj";
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 弹出键盘
     */
    public static void popUpKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext( ).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,0);
    }


    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                LogUtil.outLog("TAG","pn = "+pn);
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

}
