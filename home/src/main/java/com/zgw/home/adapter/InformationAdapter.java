package com.zgw.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.zgw.base.cache.RWSharedPreferences;
import com.zgw.home.model.NewsCategoryBean;

import java.util.List;

/**
 * Created by qmcai on 2017/5/31.
 */

public class InformationAdapter extends PagerAdapter {

    private List<NewsCategoryBean> tabs;

    private Context mContext;


    public InformationAdapter(Context context, List<NewsCategoryBean> tabs) {
        this.tabs = tabs;
        mContext = context;
    }

    @Override
    public int getCount() {
        return tabs == null ? 0 : tabs.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getPagerItem(position);
        container.addView(view);
        return view;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    protected View getPagerItem(int position) {
//        TestInforFragment browser = T.newInstance(mContext);
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
