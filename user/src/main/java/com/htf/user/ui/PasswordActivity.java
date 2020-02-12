package com.htf.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.htf.user.R;
import com.htf.user.R2;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.CheckUtil;
import com.zgw.base.util.CommonUtil;
import com.zgw.base.util.NetUtil;
import com.zgw.base.util.ZGWToast;
import com.zgw.base.component.ShapeTextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.topTitle)
    TextView topTitle;

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    ShapeTextView sendCodeBtn;

    @BindView(R2.id.nextBtn)
    ShapeTextView nextBtn;

    @BindView(R2.id.newPasswordEdit)
    EditText newPasswordEdit;

    private TimerTask timerTask;

    private Timer timer;

    private int timess;

    EditText phone_edit;

    private String phoneNumberStr;

    @BindView(R2.id.phoneCheckLayout)
    LinearLayout phoneCheckLayout;

    @BindView(R2.id.againPasswordEdit)
    EditText againPasswordEdit;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.backImageView)
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
        sendCodeBtn = $(R.id.sendCodeBtn);
        phone_edit = $(R.id.phone_edit);
        topTitle.setText("找回密码");
        topBackBtn.setOnClickListener(this);
        sendCodeBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        backImageView.setBackgroundResource(R.drawable.top_back_pic);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        } else if (id == R.id.sendCodeBtn) {
            CommonUtil.cloceInputWindow(sendCodeBtn);
            getVerifyCode();
        } else if (id == R.id.nextBtn) {

        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        if (!NetUtil.checkWirelessNetwork(this)) {
            ZGWToast.show(this, "网络未连接");
        } else if (!invalidPhoneNumber()) {
            ZGWToast.show(this, R.string.phone_number_invalid_warning);
        } else {
            sendCodeBtn.setClickable(false);
//            phoneLoginService.getSmsCode(phoneNumberStr, reuqestCode);
            startTimer();
        }
    }

    /***
     * 是否是有效的手机号码
     */
    private boolean invalidPhoneNumber() {
        phoneNumberStr = phone_edit.getText().toString();
        return CheckUtil.checkPhoneNumber(phoneNumberStr);
    }

    private void startTimer() {
        timess = 60;
        sendCodeBtn.setText(timess + "s");
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timess--;
                            if (timess <= 0) {
                                stopTimer();
                                return;
                            }
                            sendCodeBtn.setText(timess + "s");
                        }
                    });
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        sendCodeBtn.setText("重新获取");
        sendCodeBtn.setClickable(true);
        sendCodeBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer = null;
        timerTask = null;
    }
}
