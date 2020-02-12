package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationFragment extends Fragment {

    @BindView(R2.id.informationTabLayout)
    TabLayout tabLayout;

    @BindView(R2.id.viewPager)
    ViewPager viewPager;


    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
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
        View view = inflater.inflate(R.layout.information_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
        ImmersionBar.setTitleBar(this, toolbar);
        return view;

    }

    private void initView() {
        ArrayList<String> tabList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        tabList.add("全部");
        tabList.add("期货专栏");
        tabList.add("机构点评");
        tabList.add("钢铁新闻");
        tabList.add("钢厂动态");
        tabList.add("行情分析");
        tabList.add("行情分析");
        for (int i = 0; i < tabList.size(); i++) {
            fragmentList.add(NewsFragment.newInstance("tab" + i, "url == " + i));
        }
        viewPager.setOffscreenPageLimit(1);
        MyFragmentPagerAdapter viewPagerApader = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerApader.setData(tabList, fragmentList);
        viewPager.setAdapter(viewPagerApader);
        tabLayout.setupWithViewPager(viewPager);
    }
}
