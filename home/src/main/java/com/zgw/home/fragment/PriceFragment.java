package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zgw.home.R;
import com.zgw.home.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/10/8 0008.
 */

public class PriceFragment extends Fragment {

    protected final static String TITLE = "title";
    protected final static String URL = "url";

    private ViewPager priceItemViewPager;

    private TabLayout priceItemTab;

    private TextView titleTextView;

    private String title;

    public static PriceFragment newInstance(String title) {
        PriceFragment fragment = new PriceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView.setText("主要城市" + title +"价格汇总");
        ArrayList<String> tabList = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        tabList.add("0.5mm镀锌板卷");
        tabList.add("1.0mm镀锌板卷");
        tabList.add("0.467mm彩涂板卷");
        for (int i = 0; i < tabList.size(); i++) {
            fragmentList.add(PriceItemFragment.newInstance("tab" + i, "url == " + i));
        }
        priceItemViewPager.setOffscreenPageLimit(1);
        MyFragmentPagerAdapter viewPagerApader = new MyFragmentPagerAdapter(getChildFragmentManager());
        viewPagerApader.setData(tabList, fragmentList);
        priceItemViewPager.setAdapter(viewPagerApader);
        priceItemTab.setupWithViewPager(priceItemViewPager);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.price_fragment_layout, container, false);
        priceItemViewPager = view.findViewById(R.id.priceItemViewPager);
        priceItemTab = view.findViewById(R.id.priceItemTab);
        titleTextView = view.findViewById(R.id.titleTextView);
        return view;
    }
}
