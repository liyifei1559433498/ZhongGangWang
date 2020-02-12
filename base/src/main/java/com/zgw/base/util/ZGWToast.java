package com.zgw.base.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.zgw.base.R;


public class ZGWToast {

    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * 显示对话框
     *
     * @param context
     * @param resId
     */
    public static void show(Context context, int resId) {
        try {
//            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
            String message = context.getResources().getString(resId);
            showNoRepeatToast(context, message, Toast.LENGTH_SHORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param resId
     */
    public static void show(Context context, int resId, int duration) {
        try {
//            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
            String message = context.getResources().getString(resId);
            showNoRepeatToast(context, message, duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示对话框
     * @param context
     * @param resId
     * @param time
     */
//    public static void show(Context context, int resId, int time) {
//        try {
//            String message = context.getResources().getString(resId);
//            CustomToast.show(context, message, time);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        showNoRepeatToast(context, message, Toast.LENGTH_SHORT);
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message, int duration) {
        showNoRepeatToast(context, message, duration);
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 通过计算两次弹窗的出现时间，控制显示出现相同信息，较短间隔时间内显示一次
     */
    public static void showNoRepeatToast(Context context, String message, int duration) {
        try {
            if (toast == null) {
                oldMsg = message;
                //toast = Toast.makeText(context, message, duration);
                toast = new Toast(context);
                toast.setDuration(duration);
                TextView textView = getMessageTextView(context);
                textView.setBackgroundResource(R.drawable.custom_toast_circle_shape);
                if (!TextUtils.isEmpty(message) && textView != null) {
                    textView.setText(message);
                }
                toast.setView(textView);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (toast.getView() != null && (toast.getView() instanceof TextView)) {
                    TextView textView = (TextView) toast.getView();
                    textView.setText(message);
                }
                if (message.equals(oldMsg)) {
                    if (twoTime - oneTime > duration) {
                        toast.show();
                    }
                } else {
                    oldMsg = message;
//                    toast.setText(message);
                    toast.show();
                }
            }
            oneTime = twoTime;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static TextView getMessageTextView(Context context) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView textView = (TextView) inflater.inflate(R.layout.custom_toast_textview, null);
            textView.setBackgroundResource(R.drawable.custom_toast_circle_shape);
            int top = PublicViewUtils.getPxInt(8, context);
            int left = PublicViewUtils.getPxInt(17, context);
            textView.setPadding(left, top, left, top);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(14);
            return textView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TextView(context);
    }
}
