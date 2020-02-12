package com.zgw.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgw.home.R;
import com.zgw.home.activity.TodayDetailActivity;
import com.zgw.home.model.JRBJBean;

import java.util.List;

public class JRBJAdapter extends BaseAdapter {

    private List<JRBJBean> dataList;

    private Context context;

    private LayoutInflater inflater;

    public void setDataList(List<JRBJBean> dataList) {
        this.dataList = dataList;
    }

    public JRBJAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.jrbj_item_layout, null);
            viewHolder.itemLayout =  convertView.findViewById(R.id.itemLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TodayDetailActivity.class);
                ((Activity)context).startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private RelativeLayout itemLayout;
    }
}