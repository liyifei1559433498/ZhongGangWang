package com.htf.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.htf.user.R;
import com.htf.user.R2;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.component.ShapeTextView;
import com.zgw.base.util.ZGWToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.topTitle)
    TextView topTitle;

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @BindView(R2.id.findPasswordLayout)
    RelativeLayout findPasswordLayout;

    @BindView(R2.id.modifyLayout)
    RelativeLayout modifyLayout;

    @BindView(R2.id.cashLayout)
    RelativeLayout cashLayout;

    @BindView(R2.id.versionLayout)
    RelativeLayout versionLayout;

    @BindView(R2.id.userLayout)
    RelativeLayout userLayout;

    @BindView(R2.id.pushLayout)
    RelativeLayout pushLayout;

    @BindView(R2.id.aboutLayout)
    RelativeLayout aboutLayout;

    @BindView(R2.id.abortBtn)
    ShapeTextView abortBtn;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.backImageView)
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        backImageView.setBackgroundResource(R.drawable.top_back_pic);
        topTitle.setText("设置中心");
        topBackBtn.setOnClickListener(this);
        findPasswordLayout.setOnClickListener(this);
        modifyLayout.setOnClickListener(this);
        cashLayout.setOnClickListener(this);
        userLayout.setOnClickListener(this);
        versionLayout.setOnClickListener(this);
        pushLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        abortBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        } else if (id == R.id.findPasswordLayout) {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.modifyLayout) {
            ZGWToast.show(this, "are you OK?");
        } else if (id == R.id.userLayout) {
            Intent intent1 = new Intent(this, UserProtocolActivity.class);
            startActivity(intent1);
        }
    }
}
