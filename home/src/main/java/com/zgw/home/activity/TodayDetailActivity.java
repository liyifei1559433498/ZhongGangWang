package com.zgw.home.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.ui.BaseActivity;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.MyFragmentPagerAdapter;
import com.zgw.home.fragment.JRBJDetailFragment;
import com.zgw.home.fragment.PriceFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayDetailActivity extends BaseActivity {

    @BindView(R2.id.jrbjDetailTab)
    TabLayout jrbjDetailTab;

    @BindView(R2.id.jrbjDetailViewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jrbj_detail_layout);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
        initView();
    }

    private void initView() {
        ArrayList<String> tabList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        tabList.add("3.0mm \n热轧卷板Q235");
        tabList.add("3.0mm \n热轧卷板Q235");
        tabList.add("3.0mm \n热轧卷板Q235");
        for (int i = 0; i < tabList.size(); i++) {
            fragmentList.add(JRBJDetailFragment.newInstance(tabList.get(i)));
        }
//        priceViewPager.setOffscreenPageLimit(1);
        MyFragmentPagerAdapter viewPagerApader = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPagerApader.setData(tabList, fragmentList);
        viewPager.setAdapter(viewPagerApader);
        jrbjDetailTab.setupWithViewPager(viewPager);
    }
}
