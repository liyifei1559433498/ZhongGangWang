package com.htf.user.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.htf.user.R;

import java.util.List;

public class FeedBackTypeAdapter extends BaseAdapter {

    private List<String> dataList;

    private Context context;

    private LayoutInflater inflater;

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public FeedBackTypeAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.feedback_item_layout, null);
            viewHolder.typeTitle =  convertView.findViewById(R.id.typeTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.typeTitle.setText(TextUtils.isEmpty(dataList.get(position))
                ? "" : dataList.get(position));

        return convertView;
    }

    class ViewHolder {
        private TextView typeTitle;
    }
}