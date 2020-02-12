package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.adapter.HomeNewsAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class NewsFragment extends Fragment {

    protected final static String TITLE = "title";
    protected final static String URL = "url";

    private RecyclerView recycleView;

    private SmartRefreshLayout refreshLayout;

    private String title;

    private String url;

    public static NewsFragment newInstance(String channelId, String channelName) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, channelId);
        bundle.putString(URL, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add(i + "ss");

        }
        HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getActivity());
        homeNewsAdapter.setData(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycleView.setAdapter(homeNewsAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE);
        url = getArguments().getString(URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recycleView = view.findViewById(R.id.recycleView);

        refreshLayout.setRefreshHeader(new WaterDropHeader(getActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.outLog("TAG", "NewsFragment == onRefresh");
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.outLog("TAG", "NewsFragment == onLoadMore");
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
        return view;
    }


}
