package com.zgw.home.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> tab_title_list;//存放标签页标题
    private ArrayList<Fragment> fragment_list;//存放ViewPager下的Fragment

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData( ArrayList<String> tab_title_list, ArrayList<Fragment> fragment_list) {
        this.tab_title_list = tab_title_list;
        this.fragment_list = fragment_list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return (fragment_list == null || fragment_list.size() == 0) ? 0 : fragment_list.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title_list.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
