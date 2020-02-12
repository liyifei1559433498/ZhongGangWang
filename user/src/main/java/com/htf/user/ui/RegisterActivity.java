package com.htf.user.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.htf.user.R;
import com.htf.user.R2;
import com.zgw.base.component.ShapeTextView;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.CheckUtil;
import com.zgw.base.util.CommonUtil;
import com.zgw.base.util.NetUtil;
import com.zgw.base.util.ZGWToast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    TextView topTitle;

    ShapeTextView registerBtn;

    ImageView clearNumber;

    EditText phone_edit;

    private TimerTask timerTask;

    private Timer timer;

    private int timess;

    private String phoneNumberStr;

    ShapeTextView sendCodeBtn;

    @BindView(R2.id.seePassword)
    ImageView seePassword;

    ImageView backImageView;

    ImageView seePasswordAgain;

    private boolean seePasswordFlag = false;

    private boolean seePasswordAgainFlag = false;

    EditText password_edit_again;

    EditText password_edit;

    TextView warnTextView;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.loginBtn)
    TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
        loginBtn.setOnClickListener(this);
        topTitle = $(R.id.topTitle);
        registerBtn = $(R.id.registerBtn);
        clearNumber = $(R.id.clearNumber);
        phone_edit = $(R.id.phone_edit);
        sendCodeBtn = $(R.id.sendCodeBtn);
        backImageView = $(R.id.backImageView);
        seePasswordAgain = $(R.id.seePasswordAgain);
        password_edit_again = $(R.id.password_edit_again);
        password_edit = $(R.id.password_edit);
        warnTextView = $(R.id.warnTextView);
        clearNumber.setOnClickListener(this);
        topBackBtn.setOnClickListener(this);
        seePasswordAgain.setOnClickListener(this);
        seePassword.setOnClickListener(this);
        sendCodeBtn.setOnClickListener(this);
        topTitle.setText("注册");
        topTitle.setTextColor(getResources().getColor(R.color.black));
        registerBtn.setOnClickListener(this);
        backImageView.setBackgroundResource(R.drawable.top_back_pic);
        warnTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        warnTextView.getPaint().setAntiAlias(true);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.loginBtn){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.clearNumber) {
            phone_edit.setText("");
        } else if (id == R.id.topBackBtn) {
            finish();
        } else if (id == R.id.seePassword) {
            seePasswordFlag = !seePasswordFlag;
            showPassWord(password_edit, seePassword, seePasswordFlag);
        } else if (id == R.id.seePasswordAgain) {
            seePasswordAgainFlag = !seePasswordAgainFlag;
            showPassWord(password_edit_again, seePasswordAgain, seePasswordAgainFlag);
        } else if (id == R.id.sendCodeBtn) {
            CommonUtil.cloceInputWindow(sendCodeBtn);
            getVerifyCode();
        }
    }

    private void showPassWord(EditText editText, ImageView imageView, boolean flag) {
        if (flag) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//明文显示
            imageView.setBackgroundResource(R.drawable.see_password_pic);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//暗纹显示
            imageView.setBackgroundResource(R.drawable.un_see_password);
        }

        Editable etable = editText.getText();
        Selection.setSelection(etable, etable.length());
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
