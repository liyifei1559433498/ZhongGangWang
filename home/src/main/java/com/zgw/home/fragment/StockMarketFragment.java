package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.StockMarketAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockMarketFragment extends Fragment implements View.OnClickListener {

//    @BindView(R2.id.topBackBtn)
//    FrameLayout topBackBtn;

    @BindView(R2.id.addressLayout)
    LinearLayout addressLayout;

    @BindView(R2.id.filterLayout)
    LinearLayout filterLayout;

    @BindView(R2.id.stockRecycleView)
    RecyclerView stockRecycleView;

    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    private StockMarketAdapter stockMarketAdapter;

    public static StockMarketFragment newInstance() {
        StockMarketFragment fragment = new StockMarketFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_market_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
        ImmersionBar.setTitleBar(this, toolbar);
        return view;

    }

    private void initView() {
//        topBackBtn.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        filterLayout.setOnClickListener(this);

        stockMarketAdapter = new StockMarketAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity() );
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        stockRecycleView.setLayoutManager(layoutManager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("i === " + i);
        }
        stockMarketAdapter.setData(data);
        stockRecycleView.setAdapter(stockMarketAdapter);

        refreshLayout.setRefreshHeader(new WaterDropHeader(getActivity()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtil.outLog("TAG", "onRefresh");
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LogUtil.outLog("TAG", "onLoadMore");
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.addressLayout) {

        } else if (id == R.id.filterLayout) {

        }
    }
}
