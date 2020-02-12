package com.zgw.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zgw.home.model.HangQingTabBean;
import com.zgw.home.tablayout.TabManager;

import java.util.List;

public class LastHangQingAdapter extends PagerAdapter {


    private List<HangQingTabBean> tabs;

    private Context context;

    public LastHangQingAdapter(Context context) {
        this.context = context;
    }

    public void setTabsData(List<HangQingTabBean> tabs) {
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
        String tabId = tabs.get(position).getTabId();
        View hangQingView = TabManager.getHangQingView(tabId, context);
        return hangQingView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTabName();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
