package com.zgw.home.fragment;

import android.os.Bundle;

import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;

public class JRTJFragment extends LazyBaseFragment {

    public static JRTJFragment newInstance(String title) {
        Bundle args = new Bundle();
        JRTJFragment fragment = new JRTJFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.jrtj_fragment_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void fetchData() {

    }
}
