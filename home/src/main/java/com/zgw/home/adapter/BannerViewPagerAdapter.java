package com.zgw.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zgw.home.R;

import java.util.List;

/**
 * Created by admin on 2018/10/26.
 */

public class BannerViewPagerAdapter extends PagerAdapter {

    private List<ImageView> dataList;

    private Context context;

    private RequestManager requestManager;

    public BannerViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void setDataList(List<ImageView> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList == null || dataList.size() == 0) {
            return 0;
        } else {
            return (dataList != null && dataList.size() == 1) ? 1 : Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % dataList.size();
        ImageView imageView = dataList.get(index);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        String path = dataList.get(index).getPath();
        requestManager.load(R.drawable.banner_bg)
                .into(imageView);
        if (imageView.getParent() == null) {
            container.addView(imageView);
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
