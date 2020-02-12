package com.zgw.home.fragment;

import android.os.Bundle;

import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;

public class YXSJFragment extends LazyBaseFragment {

    public static YXSJFragment newInstance(String title) {
        Bundle args = new Bundle();
        YXSJFragment fragment = new YXSJFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.yxsj_fragment_layout;
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
