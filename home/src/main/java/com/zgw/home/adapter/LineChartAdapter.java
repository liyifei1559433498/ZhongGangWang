package com.zgw.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zgw.home.view.LineChartView;

import java.util.List;

public class LineChartAdapter extends PagerAdapter {


    private List<String> tabs;

    private Context context;

    public LineChartAdapter( Context context) {
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
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = getPagerItem(position);
        container.addView(view);
        return view;
    }

    protected View getPagerItem(int position) {
//        View view = mLineList.get(position);
        LineChartView lineChartView = LineChartView.newInstance(context);
        return lineChartView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
