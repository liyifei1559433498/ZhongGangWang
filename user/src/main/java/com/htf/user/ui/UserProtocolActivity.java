package com.htf.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.htf.user.R;
import com.htf.user.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProtocolActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @BindView(R2.id.topTitle)
    TextView topTitle;

    @BindView(R2.id.topLayout)
    RelativeLayout topLayout;

    @BindView(R2.id.backImageView)
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocol_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topTitle.setText("用户服务协议");
        topLayout.setBackgroundResource(R.color.white);
        topTitle.setTextColor(getResources().getColor(R.color.black));
        backImageView.setBackgroundResource(R.drawable.top_back_pic);
        topBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        }
    }
}
