package com.zgw.home.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zgw.base.component.LoadDialogUtils;
import com.zgw.base.component.ScrollTextViewLayout;
import com.zgw.base.model.TestBean;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.base.util.CommonUtil;
import com.zgw.base.util.LogUtil;
import com.zgw.base.util.PublicViewUtils;
import com.zgw.base.util.ZGWToast;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.activity.PriceSummaryActivity;
import com.zgw.home.activity.StockMapActivity;
import com.zgw.home.activity.TodayPriceActivity;
import com.zgw.home.adapter.BannerViewPagerAdapter;
import com.zgw.home.adapter.BarChartAdapter;
import com.zgw.home.adapter.HomeNewsAdapter;
import com.zgw.home.adapter.LineChartAdapter;
import com.zgw.home.baseadapter.MainService;
import com.zgw.network_api.RetrofitProvider;
import com.zgw.network_api.extension.BaseObserver;
import com.zgw.network_api.rx.RxProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 懒加载集成LazyBaseFragment
 */
public class TotalTabFragment extends LazyBaseFragment implements View.OnClickListener {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R2.id.viewpager1)
    ViewPager zgwViewpager;

    @BindView(R2.id.viewpager2)
    ViewPager zgwViewpager2;

    @BindView(R2.id.tabLayout1)
    TabLayout tabLayout1;

    @BindView(R2.id.tabLayout2)
    TabLayout tabLayout2;

    @BindView(R2.id.bannerViewPager)
    ViewPager bannerViewPager;

    @BindView(R2.id.point_group)
    LinearLayout pointGroup;

    @BindView(R2.id.scrollTextView)
    ScrollTextViewLayout scrollTextView;

    @BindView(R2.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R2.id.homeRefreshLayout)
    SmartRefreshLayout homeRefreshLayout;

    @BindView(R2.id.priceMap)
    RelativeLayout priceMap;

    @BindView(R2.id.todayPriceBtn)
    LinearLayout todayPriceBtn;

    @BindView(R2.id.priceSummaryLayout)
    RelativeLayout priceSummaryLayout;

    @BindView(R2.id.contentLayout)
    LinearLayout contentLayout;

    @BindView(R2.id.helpLayout)
    LinearLayout helpLayout;

    @BindView(R2.id.testBtn)
    LinearLayout testBtn;

    private BarChartAdapter barChartAdapter;

    private LineChartAdapter lineChartAdapter;

    private HomeNewsAdapter homeNewsAdapter;

    private List<String> mLineTabList = new ArrayList<>();

    private ProgressDialog progressDialog;

    private Handler handler = new Handler();

    private RequestManager requestManager;

    public static TotalTabFragment newInstance() {
        TotalTabFragment fragment = new TotalTabFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setData() {
        ArrayList<ImageView> mList1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(getContext());
            mList1.add(imageView);
        }
        //小原点
        pointGroup.removeAllViews();
        for (int i = 0; i < mList1.size(); i++) {
            // 制作底部小圆点
            ImageView pointImage = new ImageView(getContext());
            pointImage.setImageResource(R.drawable.viewpager_dot);

            // 设置小圆点的布局参数
            int PointSize = PublicViewUtils.getPxInt(10, getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PointSize, PointSize);
            params.leftMargin = PointSize;
            pointImage.setSelected(false);
            pointImage.setLayoutParams(params);
            // 添加到容器里
            pointGroup.addView(pointImage);
        }
        requestManager = Glide.with(getContext());
        BannerViewPagerAdapter bannerAdapter = new BannerViewPagerAdapter(getContext());
        bannerAdapter.setRequestManager(requestManager);
        bannerAdapter.setDataList(mList1);
        bannerViewPager.setAdapter(bannerAdapter);
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        List<String> dataLst = new ArrayList<>();
        dataLst.add("sssssssssssssssssssss");
        dataLst.add("aaaaaaaaaaaaaaaaaaaa");
//        publicMethod.setMarketMessage(dataLst, scrollTextView);

        List<String> mTabList2 = new ArrayList<>();
        mTabList2.add("高钢");
        mTabList2.add("螺丝钢");
        mTabList2.add("普中板");
        mTabList2.add("热轧板卷");
        mTabList2.add("冷轧板卷");
        mTabList2.add("冷轧板卷");
        mTabList2.add("冷轧板卷");
        mLineTabList.clear();
        mLineTabList.addAll(mTabList2);

        if (lineChartAdapter == null) {
            lineChartAdapter = new LineChartAdapter(getContext());
        }
        zgwViewpager.setOffscreenPageLimit(mLineTabList.size());
        lineChartAdapter.setTabsData(mLineTabList);
        lineChartAdapter.notifyDataSetChanged();

        ArrayList<String> mList3 = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            mList3.add(i + "");
        }
        if (barChartAdapter == null) {
            barChartAdapter = new BarChartAdapter(getContext());
        }
        barChartAdapter.setTabsData(mList3);
        barChartAdapter.notifyDataSetChanged();

        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add(i + "== shajhsj");

        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeNewsAdapter = new HomeNewsAdapter(getContext());
        //设置为垂直布局，这也是默认的

        homeNewsAdapter.setData(mList);
        recyclerView.setAdapter(homeNewsAdapter);
//        homeNewsAdapter.notifyDataSetChanged();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadDialogUtils.closeDialog(progressDialog);
            }
        }, 500);

    }

    private void testNetWork() {//获取用户数据
        if (getContext() == null) return;
        RetrofitProvider.getService(MainService.class)
                .test("-1", "1", "10", "0", "60")
                .compose(RxProgress.bindToLifecycle((BaseActivity) getContext(), "正在获取信息", false))
                .subscribe(new BaseObserver<TestBean>() {
                    @Override
                    public void onSuccess(TestBean testBean) {
                        homeRefreshLayout.finishRefresh();
                        homeRefreshLayout.finishLoadMore();
                        setData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        homeRefreshLayout.finishRefresh();
                        homeRefreshLayout.finishLoadMore();
                        showFailure();
                        LoadDialogUtils.closeDialog(progressDialog);
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.total_fragment_layout;
    }

    protected void initView() {
        priceSummaryLayout.setOnClickListener(this);
        helpLayout.setOnClickListener(this);
        todayPriceBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        priceMap.setOnClickListener(this);

        lineChartAdapter = new LineChartAdapter(getContext());
        zgwViewpager.setAdapter(lineChartAdapter);
        tabLayout1.setupWithViewPager(zgwViewpager);

        barChartAdapter = new BarChartAdapter(getContext());
        zgwViewpager2.setAdapter(barChartAdapter);
        tabLayout2.setupWithViewPager(zgwViewpager2);

//        设置布局管理器
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        recyclerView.setFocusableInTouchMode(false);

        homeRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        homeRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        homeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                fetchData();
            }
        });
        homeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                fetchData();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void fetchData() {
        progressDialog = CommonUtil.createProgressDialog(getContext(), "");
        testNetWork();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.priceMap) {
            Intent mapIntent = new Intent(getContext(), StockMapActivity.class);
            startActivity(mapIntent);
        } else if (id == R.id.todayPriceBtn) {
            Intent intent = new Intent(getContext(), TodayPriceActivity.class);
            startActivity(intent);
        } else if (id == R.id.priceSummaryLayout) {
            Intent intent1 = new Intent(getContext(), PriceSummaryActivity.class);
            startActivity(intent1);
        } else if (id == R.id.testBtn) {
            CC.obtainBuilder("videoComponent")
                    .setActionName("VideoActivity")
                    .addParam("test", "url")
                    .build()
                    .call();
        } else if (id == R.id.helpLayout) {
            CC.obtainBuilder("userComponent")
                    .setActionName("LoginActivity")
                    .addParam("test", "纳斯hi试试")
                    .setContext(getContext())
                    .build()
                    .callAsyncCallbackOnMainThread(new IComponentCallback() {
                        @Override
                        public void onResult(CC cc, CCResult result) {
                            if (result.isSuccess()) {
                                String data = (String) result.getDataMap().get("biubiu");
                            } else {
                                String errorMessage = result.getErrorMessage();
                                LogUtil.outLog("TAG", "errorMessage == " + errorMessage);
                                ZGWToast.show(getContext(), "登录取消");
                            }

                        }
                    });
        }
    }

}
