package com.zgw.home.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.zgw.home.model.NewsCategoryBean;
import com.zgw.home.tablayout.TabManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private List<NewsCategoryBean> tabs;//存放标签页标题

    private List<Fragment> fragment_list;//存放ViewPager下的Fragment

    public TabFragmentAdapter(FragmentManager fm, List<NewsCategoryBean> tabs, List<Fragment> fragment_list) {
        super(fm);
        this.tabs = tabs;
        this.fragment_list = fragment_list;
    }

    public void setData(List<NewsCategoryBean> tabs, ArrayList<Fragment> fragment_list) {
        this.tabs = tabs;
        this.fragment_list = fragment_list;
    }

    @Override
    public Fragment getItem(int position) {
        return TabManager.fragmentMap.get(tabs.get(position).getCategoryId());
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
