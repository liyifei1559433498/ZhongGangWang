package com.zgw.base.util;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgw.base.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static android.content.Context.ACTIVITY_SERVICE;

public class CommonUtil {

    private static Map<Context, ProgressDialog> progressDialogMap;

    /**
     * gzip
     *
     * @param data
     * @return byte[]
     */
    public static byte[] decompress2(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }


    private static void removeDialog(DialogInterface progressdialog) {
        if (progressDialogMap != null) {
            for (Context key : progressDialogMap.keySet()) {
                if (progressDialogMap.get(key) == progressdialog) {
                    progressDialogMap.remove(key);
                    break;
                }
            }
        }
    }

    /**
     * 显示消息
     */
    public static void showMessage(Context context, String message) {
        ZGWToast.show(context, message);//这里是service回调公用方法调用时机会根据网络请求情况，使用自定义会出现重叠的弹框提示，这里不可预知哪里会出现弹框，暂时改回系统toast。
    }

    /**
     * 如果单击确定的时候，该界面有输入法弹出窗，则关闭
     */
    public static void cloceInputWindow(View v){
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    /*** 只显示前三后四*/
    public static String interceptCommonPartString(String text){
        try {
            if (!TextUtils.isEmpty(text) && text.length() >= 7) {
                String tempString=text;
                tempString=text.substring(0, 3);
                for (int i=0; i<text.length()-7; i++) {
                    tempString=tempString+"*";
                }
                tempString=tempString+text.substring(text.length()-4);

                return tempString;
            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static ProgressDialog createProgressDialog(Context context, String loadingStr) {
        try {
            if (progressDialogMap == null) {
                progressDialogMap = new HashMap<>();
            } else {
                ProgressDialog tmpDialog = progressDialogMap.get(context);
                if (tmpDialog != null) {
                    tmpDialog.dismiss();
                    progressDialogMap.remove(context);
                    tmpDialog = null;
                }
            }
            ProgressDialog mProgressdialog = new ProgressDialog(context, R.style.loading_dialog);
            progressDialogMap.put(context, mProgressdialog);
            mProgressdialog.show();
            mProgressdialog.setCanceledOnTouchOutside(false);
            View dialogView = getView(context, loadingStr);
            mProgressdialog.getWindow().setContentView(dialogView);
            mProgressdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    removeDialog(dialog);
                }
            });
            return mProgressdialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static View getView(Context context, String loadingStr) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView loadText = (TextView) view.findViewById(R.id.loadText);
        loadText.setText(loadingStr);
        if(imageView != null) {
            ((AnimationDrawable)imageView.getBackground()).start();
        }
        return view;
    }

    public static void closeProgressDialog(ProgressDialog progressdialog ) {
        try {
            if (progressdialog != null && progressdialog.isShowing()&& isAppRunning(progressdialog.getContext())) {
                progressdialog.cancel();
            }
            removeDialog(progressdialog);
            progressdialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isAppRunning(Context context) {
        String packageName = getPackageName(context);
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
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

}
