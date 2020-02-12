package com.zgw.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zgw.home.view.BarChartView;

import java.util.List;

public class BarChartAdapter extends PagerAdapter {


    private List<String> tabs;

    private Context context;

    public BarChartAdapter(Context context) {
        this.context = context;
    }

    public void setTabsData(List<String> tabs) {
        this.tabs = tabs;
    }

    @Override
    public int getCount() {
        return (tabs == null || tabs.size() == 0) ? 0 :tabs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = getPagerItem(position);
        container.addView(view);
        return view;
    }

    protected View getPagerItem(int position) {
        BarChartView barChartView = BarChartView.newInstance(context);
        return barChartView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
