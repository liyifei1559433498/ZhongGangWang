package com.zgw.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zgw.base.ui.BaseActivity;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.MyFragmentPagerAdapter;
import com.zgw.home.fragment.TodayFragment;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/10/7 0007.
 */

public class TodayPriceActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.todayViewPager)
    ViewPager todayViewPager;

    @BindView(R2.id.todayTabLayout)
    TabLayout todayTabLayout;

    @BindView(R2.id.topTitle)
    TextView topTitle;

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_price_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topTitle.setText("今日报价");
        topBackBtn.setOnClickListener(this);
        ArrayList<String> tabTitleList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        tabTitleList.add("卷板");
        tabTitleList.add("中板");
        tabTitleList.add("建材");
        tabTitleList.add("大中建材");
        tabTitleList.add("H型钢");
        tabTitleList.add("无缝管");
        tabTitleList.add("直缝管");
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));
        fragmentList.add(TodayFragment.newInstance("", ""));

        todayViewPager.setOffscreenPageLimit(tabTitleList.size());
        MyFragmentPagerAdapter viewPagerApader = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPagerApader.setData(tabTitleList, fragmentList);
        todayViewPager.setAdapter(viewPagerApader);
        todayTabLayout.setupWithViewPager(todayViewPager);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        }
    }
}
