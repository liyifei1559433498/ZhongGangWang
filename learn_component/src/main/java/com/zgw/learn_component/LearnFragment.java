package com.zgw.learn_component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.billy.cc.core.component.CC;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnFragment extends Fragment implements View.OnClickListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.videoBtn)
    TextView videoBtn;

    public static LearnFragment newInstance() {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.learn_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ImmersionBar.setTitleBar(this, toolbar);
        videoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.videoBtn) {
            CC.obtainBuilder("videoComponent")
                    .setActionName("VideoActivity")
                    .addParam("test", "url")
                    .build()
                    .call();
        }
    }
}
