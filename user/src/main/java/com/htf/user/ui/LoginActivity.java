package com.htf.user.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.htf.user.R;
import com.htf.user.R2;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.LogUtil;
import com.zgw.base.util.PublicMethod;
import com.zgw.base.util.ZGWToast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView topTitle;

    TextView rightTitle;

    TextView codeLogin;

    TextView warnTextView;

    TextView findPassword;

    EditText phone_edit;

    EditText password_edit;

    ImageView clearNumber;

    ImageView seePassword;

    private boolean seePasswordFlag = false;

    ImageView backImageView;

    FrameLayout topBackBtn;

    private String callId;

    private CCResult result;

    @BindView(R2.id.qq_login)
    ImageView qq_login;

    @BindView(R2.id.we_chat)
    ImageView we_chat;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        callId = getIntent().getStringExtra(CCUtil.EXTRA_KEY_CALL_ID);
        String test = CCUtil.getNavigateParam(this, "test", "");
        LogUtil.outLog("TAG", "Param == " + test);
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        topTitle = $(R.id.topTitle);
        rightTitle = $(R.id.rightTitle);
        codeLogin = $(R.id.codeLogin);
        warnTextView = $(R.id.warnTextView);
        findPassword = $(R.id.findPassword);
        phone_edit = $(R.id.phone_edit);
        password_edit = $(R.id.password_edit);
        clearNumber = $(R.id.clearNumber);
        seePassword = $(R.id.seePassword);
        backImageView = $(R.id.backImageView);
        topBackBtn = $(R.id.topBackBtn);
        topTitle.setText("登录");
        rightTitle.setText("注册");
        backImageView.setBackgroundResource(R.drawable.top_back_pic);
        seePassword.setOnClickListener(this);
        codeLogin.setOnClickListener(this);
        clearNumber.setOnClickListener(this);
        topBackBtn.setOnClickListener(this);
        findPassword.setOnClickListener(this);
        rightTitle.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        we_chat.setOnClickListener(this);
        warnTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        warnTextView.getPaint().setAntiAlias(true);
    }

    @Override
    public void onClick(View view) {
        UMShareAPI mShareAPI = UMShareAPI.get(this);
        int id = view.getId();
        if (id == R.id.seePassword) {
            seePasswordFlag = !seePasswordFlag;
            showPassWord();
        } else if (id == R.id.rightTitle) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.codeLogin) {
            Intent codeIntent = new Intent(this, CodeLoginActivity.class);
            startActivity(codeIntent);
        } else if (id == R.id.clearNumber) {
            phone_edit.setText("");
        } else if (id == R.id.findPassword) {
            Intent intent1 = new Intent(this, PasswordActivity.class);
            startActivity(intent1);
        } else if (id == R.id.topBackBtn) {
           if (TextUtils.isEmpty(callId)) {

           } else {
               result = new CCResult();
               result.setSuccess(true);
               result.setErrorMessage("");
               result.addData("biubiu", "啊哈哈哈哈");
           }
            finish();
        } else if (id == R.id.qq_login) {
            if (PublicMethod.isFastDoubleClick()) return;
            boolean isQQInstall = mShareAPI.isInstall(this, SHARE_MEDIA.QQ);
            if (isQQInstall) {
                thirdLogin(SHARE_MEDIA.QQ);
            } else {
                ZGWToast.show(this,"您尚未安装QQ");
            }
        } else if (id == R.id.we_chat) {
            if (PublicMethod.isFastDoubleClick()) return;
            boolean isWXInstall = mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
            if (isWXInstall) {
                thirdLogin(SHARE_MEDIA.WEIXIN);
            } else {
                ZGWToast.show(this, "您尚未安装微信");
            }
        }
    }

    /*获取第三方用户信息，昵称、头像等*/
    private void thirdLogin(SHARE_MEDIA share_media) {
        try {
            UMShareConfig config = new UMShareConfig();
            config.isNeedAuthOnGetUserInfo(true);
            UMShareAPI.get(this).setShareConfig(config);

            UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {

                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    if (map != null) {
                        String string = map.toString();
                        String third_type = ""; //用于区分平台（后台定义的）
                        String authToken = "";//第三方token
                        String headPic = "";//第三方头像
                        String nickName = "";//第三方昵称
                        String unionid = "";//
                        String openid = "";//
                        if (map != null) {
                            authToken = map.get("access_token");
                            nickName = map.get("name");
                            headPic = map.get("iconurl");
                            unionid = map.get("unionid");//openid不确定唯一,一个开发者账号下unionid唯一
                            openid = map.get("openid");//openid不确定唯一,一个开发者账号下unionid唯一
                            if (SHARE_MEDIA.QQ.equals(share_media)) {
                                third_type = "qq";
                            } else if (SHARE_MEDIA.WEIXIN.equals(share_media)) {
                                third_type = "wx";
                            }
                        }
                        LogUtil.outLog("TAG", "openid == " + openid);
                        LogUtil.outLog("TAG", "nickName == " + nickName);
                        LogUtil.outLog("TAG", "map == " + map.toString());
                    } else {
                        LogUtil.outLog("TAG", "微信获取取为空");
                    }
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    showMessage(share_media, "失败");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    showMessage(share_media, "取消");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessage(SHARE_MEDIA platform, String message) {
        ZGWToast.show(this, /*"来自" + mPlatform +*/ "登录" + message /*+ message*/);
    }

    private void showPassWord() {
        if (seePasswordFlag) {
            password_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//明文显示
            seePassword.setBackgroundResource(R.drawable.see_password_pic);
        } else {
            password_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());//暗纹显示
            seePassword.setBackgroundResource(R.drawable.un_see_password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callId != null) {
            if (result == null) {
                result = CCResult.error("登录取消");
            }
            CC.sendCCResult(callId, result);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
