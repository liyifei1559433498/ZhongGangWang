package com.zgw.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.ui.BaseActivity;
import com.zgw.home.R;
import com.zgw.home.adapter.PriceSummaryAdapter;
import com.zgw.home.model.PriceSummaryBean;
import com.zgw.home.R2;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/10/8 0008.
 */

public class PriceSummaryActivity extends BaseActivity implements View.OnClickListener {

    private List<PriceSummaryBean> dataList = new ArrayList<>();

    @BindView(R2.id.recycleView)
    RecyclerView recycleView;

    @BindView(R2.id.topTitle)
    TextView topTitle;

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_summary_layout);
        ButterKnife.bind(this);
        initView();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                onRefreshEmpty();
            }
        }, 3000);
    }

    private void initView() {
        ImmersionBar.with(this).statusBarView(R.id.top_view).statusBarColor(R.color.orange).init();
        setLoadSir(recycleView);
        showLoading();
        topBackBtn.setOnClickListener(this);
        topTitle.setText("价格汇总");
        PriceSummaryBean summaryBean = new PriceSummaryBean();
        summaryBean.setTitle("板材");
        summaryBean.setIcon(R.drawable.baicai_icon);
        dataList.add(summaryBean);
        PriceSummaryBean summaryBean1 = new PriceSummaryBean();
        summaryBean1.setTitle("建材");
        summaryBean1.setIcon(R.drawable.jiancai_icon);
        dataList.add(summaryBean1);

        PriceSummaryBean summaryBean2 = new PriceSummaryBean();
        summaryBean2.setTitle("型材");
        summaryBean2.setIcon(R.drawable.xingcai_icon);
        dataList.add(summaryBean2);

        PriceSummaryAdapter summaryAdapter = new PriceSummaryAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //设置布局管理器
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        recycleView.setFocusableInTouchMode(false);
        recycleView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        summaryAdapter.setData(dataList);
        recycleView.setAdapter(summaryAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        }
    }

    @Override
    protected void onRetryBtnClick() {
        super.onRetryBtnClick();
        showLoading();
        showContent();
    }
}
