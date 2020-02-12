package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.ZiXunAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZiXunFragment extends LazyBaseFragment {


    @BindView(R2.id.listView)
    ListView listView;

    private ZiXunAdapter adapter;

    private List<String> dataList = new ArrayList<>();

    private RequestManager requestManager;

    public static ZiXunFragment newInstance(String title) {
        Bundle args = new Bundle();
        ZiXunFragment fragment = new ZiXunFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.zixun_fragment_layout;
    }

    @Override
    protected void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.zixun_header_view, null);
        listView.addHeaderView(view);
    }

    @Override
    protected void initData() {
        if (adapter == null) {
            adapter = new ZiXunAdapter(getContext());
            listView.setAdapter(adapter);
        }

        for (int i = 0; i < 20; i++) {
            dataList.add("item" + i);
        }
        requestManager = Glide.with(getContext());
        adapter.setDataList(dataList);
        adapter.setRequestManager(requestManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fetchData() {

    }
}
