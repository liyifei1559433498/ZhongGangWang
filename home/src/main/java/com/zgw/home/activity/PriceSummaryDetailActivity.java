package com.zgw.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zgw.base.ui.BaseActivity;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.MyFragmentPagerAdapter;
import com.zgw.home.fragment.PriceFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/10/8 0008.
 */

public class PriceSummaryDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @BindView(R2.id.topTitle)
    TextView topTitle;

    private String title;

    @BindView(R2.id.priceViewPager)
    ViewPager priceViewPager;

    @BindView(R2.id.priceDetailTab)
    TabLayout priceDetailTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_summary_detail_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        topBackBtn.setOnClickListener(this);
        topTitle.setText(title + "价格汇总");


        ArrayList<String> tabList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        tabList.add("涂镀");
        tabList.add("热轧带钢");
        tabList.add("中厚板");
        tabList.add("冷轧板卷");
        tabList.add("热扎板卷");
        for (int i = 0; i < tabList.size(); i++) {
            fragmentList.add(PriceFragment.newInstance(tabList.get(i)));
        }
//        priceViewPager.setOffscreenPageLimit(1);
        MyFragmentPagerAdapter viewPagerApader = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPagerApader.setData(tabList, fragmentList);
        priceViewPager.setAdapter(viewPagerApader);
        priceDetailTab.setupWithViewPager(priceViewPager);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        }
    }
}
