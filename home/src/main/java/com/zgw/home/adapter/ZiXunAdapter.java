package com.zgw.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.zgw.base.GlideApp;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;

import java.util.List;

public class ZiXunAdapter extends BaseAdapter {

    private List<String> dataList;

    private Context mContext;

    private RequestManager requestManager;

    private LayoutInflater inflater;

    public ZiXunAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public int getCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.zixun_item_layout, null);
            viewHolder.titleTv = convertView.findViewById(R.id.titleTv);
            viewHolder.newTab = convertView.findViewById(R.id.newTab);
            viewHolder.ziXunPic = convertView.findViewById(R.id.ziXunPic);
            viewHolder.timeTv = convertView.findViewById(R.id.timeTv);
            viewHolder.readCount = convertView.findViewById(R.id.readCount);
            viewHolder.noPicReadCount = convertView.findViewById(R.id.noPicReadCount);
            viewHolder.noPicTimeTv = convertView.findViewById(R.id.noPicTimeTv);
            viewHolder.noPicLayout = convertView.findViewById(R.id.noPicLayout);
            viewHolder.picLayout = convertView.findViewById(R.id.picLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position > 5) {
            viewHolder.picLayout.setVisibility(View.VISIBLE);
            viewHolder.noPicLayout.setVisibility(View.GONE);
            viewHolder.titleTv.setText("河钢集团5月下旬建筑钢材产品价格政策，阿贾克斯教科书上看见爱上会计师");
            viewHolder.newTab.setText("今日推荐");
            viewHolder.timeTv.setText("12-04 07:20");
            viewHolder.readCount.setText("1243 阅读");
            viewHolder.ziXunPic.setVisibility(View.VISIBLE);

            requestManager.load(R.drawable.zixun_test_deflut_icon)
                    .error(R.drawable.home_top_left_pic)
                    .placeholder(R.drawable.home_top_left_pic)
                    .into(viewHolder.ziXunPic);


        } else {
            viewHolder.picLayout.setVisibility(View.GONE);
            viewHolder.noPicLayout.setVisibility(View.VISIBLE);
            viewHolder.ziXunPic.setVisibility(View.GONE);
            viewHolder.titleTv.setText("河钢集团5月下旬建筑钢材产品价格政策，阿贾克斯教科书上看见爱上会计师");
            viewHolder.newTab.setText("今日推荐");
            viewHolder.noPicTimeTv.setText("12-04 07:20");
            viewHolder.noPicReadCount.setText("1243 阅读");
        }
        return convertView;
    }

    class ViewHolder {
        private TextView titleTv;
        private ImageView ziXunPic;
        private TextView newTab;
        private TextView timeTv;
        private TextView readCount;
        private TextView noPicTimeTv;
        private TextView noPicReadCount;
        private LinearLayout noPicLayout;
        private LinearLayout picLayout;
    }
}
