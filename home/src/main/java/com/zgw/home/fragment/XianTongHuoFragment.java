package com.zgw.home.fragment;

import android.os.Bundle;

import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;

public class XianTongHuoFragment extends LazyBaseFragment {

    public static XianTongHuoFragment newInstance(String title) {
        Bundle args = new Bundle();
        XianTongHuoFragment fragment = new XianTongHuoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.xiantonghuo_fragment_layout;
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
