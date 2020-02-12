package com.zgw.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zgw.home.R;

import java.util.List;

public class JRBJDetailAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater inflater;

    private List<String> dataList;

    public JRBJDetailAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView  == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.jrbj_detail_item_layout, null);
            viewHolder.firstTv = convertView.findViewById(R.id.firstTv);
            viewHolder.secondTv = convertView.findViewById(R.id.secondTv);
            viewHolder.fourthTv = convertView.findViewById(R.id.fourthTv);
            viewHolder.thirdTv = convertView.findViewById(R.id.thirdTv);
            viewHolder.fifthTv = convertView.findViewById(R.id.fifthTv);
            viewHolder.sixthTv = convertView.findViewById(R.id.sixthTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        private TextView firstTv;
        private TextView secondTv;
        private TextView thirdTv;
        private TextView fourthTv;
        private TextView fifthTv;
        private TextView sixthTv;
    }
}
