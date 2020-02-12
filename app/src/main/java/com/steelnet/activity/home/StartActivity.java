package com.steelnet.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.steelnet.activity.R;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.PublicMethod;
import com.zgw.base.util.ZGWToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {

    @BindView(R.id.openImgFirst)
    ImageView openImgFirst;


    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setContentView(R.layout.start_layout);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        try {
            setFirstImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置第一张开机图
     */
    private void setFirstImage() {
        try {
            openImgFirst.setImageResource(R.drawable.openimg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PublicMethod.checkWirelessNetwork(this)) {
            showNoConnectionDialog();
        } else {
            long time = 1500;
            if (PublicMethod.isMobileNet(this)) {
                time = 1500;
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    turnActivity();
                }
            }, time);
        }
    }

    private void showNoConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setMessage(R.string.no_connection);
        builder.setTitle(R.string.no_connection_title);
        builder.setPositiveButton(R.string.settings,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        } else {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                        finish();
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        return;
                    }
                });
        builder.setNeutralButton(R.string.enter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        turnActivity();
                        ZGWToast.show(StartActivity.this, "网络不可用");
                        finish();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                finish();
                return;
            }
        });

        builder.show();
    }

    /**
     * 跳转下一页
     */
    public void turnActivity() {
//        boolean isFirst = shellRW.getBooleanValue(ShellRWConstants.USERINFO, "isFirst", false);
//        if (isFirst) {
            Intent in = new Intent(StartActivity.this, MainActivity.class);
            startActivity(in);
            StartActivity.this.finish();
//        } else {
//            shellRW.putBooleanValue(ShellRWConstants.USERINFO, "isFirst", true);
//            Intent in = new Intent(StartActivity.this, GuideActivity.class);
//            startActivity(in);
//            StartActivity.this.finish();
//        }

    }
}
