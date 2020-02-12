package com.zgw.home.fragment;

import android.os.Bundle;

import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;

public class HGJJFragment extends LazyBaseFragment {

    public static HGJJFragment newInstance(String title) {
        Bundle args = new Bundle();
        HGJJFragment fragment = new HGJJFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.hgjj_fragment_layout;
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
