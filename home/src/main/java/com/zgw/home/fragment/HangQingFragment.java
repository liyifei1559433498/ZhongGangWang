package com.zgw.home.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.tabs.TabLayout;
import com.zgw.base.ui.LazyBaseFragment;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.BannerViewPagerAdapter;
import com.zgw.home.adapter.LastHangQingAdapter;
import com.zgw.home.model.HangQingTabBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HangQingFragment extends LazyBaseFragment {

    @BindView(R2.id.hqTabLayout)
    TabLayout hqTabLayout;

    @BindView(R2.id.bannerViewPager)
    ViewPager bannerViewPager;

    @BindView(R2.id.hqViewPager)
    ViewPager hqViewPager;

    private LastHangQingAdapter hangQingAdapter;

    private RequestManager requestManager;

    public static HangQingFragment newInstance(String title) {
        Bundle args = new Bundle();
        HangQingFragment fragment = new HangQingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.hangqing_fragment_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(getContext());
            imgList.add(imageView);
        }
        requestManager = Glide.with(getContext());
        BannerViewPagerAdapter bannerAdapter = new BannerViewPagerAdapter(getContext());
        bannerAdapter.setRequestManager(requestManager);
        bannerAdapter.setDataList(imgList);
        bannerViewPager.setAdapter(bannerAdapter);


        List<HangQingTabBean> hqTabList = new ArrayList<>();
        HangQingTabBean zxhqBean = new HangQingTabBean();
        zxhqBean.setTabId("zxhq");
        zxhqBean.setTabName("最新行情");
        hqTabList.add(zxhqBean);

//        HangQingTabBean xqdtBean = new HangQingTabBean();
//        xqdtBean.setTabId("xqdt");
//        xqdtBean.setTabName("行情地图");
//        hqTabList.add(xqdtBean);


        HangQingTabBean jrkxBean = new HangQingTabBean();
        jrkxBean.setTabId("jrkx");
        jrkxBean.setTabName("今日快讯");
        hqTabList.add(jrkxBean);

        HangQingTabBean jgzsBean = new HangQingTabBean();
        jgzsBean.setTabId("jgzs");
        jgzsBean.setTabName("价格走势");
        hqTabList.add(jgzsBean);

        HangQingTabBean jrbjBean = new HangQingTabBean();
        jrbjBean.setTabId("jrbj");
        jrbjBean.setTabName("今日报价");
        hqTabList.add(jrbjBean);

        HangQingTabBean gshqBean = new HangQingTabBean();
        gshqBean.setTabId("gshq");
        gshqBean.setTabName("钢市行情");
        hqTabList.add(gshqBean);

        HangQingTabBean jghzBean = new HangQingTabBean();
        jghzBean.setTabId("jghz");
        jghzBean.setTabName("价格汇总");
        hqTabList.add(jghzBean);

        HangQingTabBean mrfxBean = new HangQingTabBean();
        mrfxBean.setTabId("mrfx");
        mrfxBean.setTabName("每日分析");
        hqTabList.add(mrfxBean);

        HangQingTabBean mzpxBean = new HangQingTabBean();
        mzpxBean.setTabId("mzpx");
        mzpxBean.setTabName("每周评述");
        hqTabList.add(mzpxBean);

        if (hangQingAdapter == null) {
            hangQingAdapter = new LastHangQingAdapter(getContext());
            hqViewPager.setAdapter(hangQingAdapter);
            hqTabLayout.setupWithViewPager(hqViewPager);
        }
        hangQingAdapter.setTabsData(hqTabList);
        hangQingAdapter.notifyDataSetChanged();


    }

    @Override
    public void fetchData() {

    }
}
