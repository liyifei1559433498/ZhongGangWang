package com.zgw.home.fragment;

import android.os.Bundle;

import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;

public class ZhaoHuoFragment extends LazyBaseFragment {

    public static ZhaoHuoFragment newInstance(String title) {
        Bundle args = new Bundle();
        ZhaoHuoFragment fragment = new ZhaoHuoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.zhaohuo_fragment_layout;
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
