package com.zgw.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgw.base.GlideApp;
import com.zgw.home.R;

import java.util.List;

public class TotalNewsAdapter extends BaseAdapter {

    private List<String> data;

    private Context context;

    private LayoutInflater layoutInflater;

    public void setData(List<String> data) {
        this.data = data;
    }
    public TotalNewsAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return (data == null || data.size() == 0) ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            convertView = layoutInflater.inflate(R.layout.home_news_item, null);
            viewHolder.newsTitle =  convertView.findViewById(R.id.newsTitle);
            viewHolder.newsState =  convertView.findViewById(R.id.newsState);
            viewHolder.newTime =  convertView.findViewById(R.id.newTime);
            viewHolder.readPerson =  convertView.findViewById(R.id.readPerson);
            viewHolder.newsPic =  convertView.findViewById(R.id.newsPic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.newsTitle.setText("22日黑色系TOP20仓单日报：黑色资金");
        viewHolder.newsState.setText("机构点评");
        viewHolder.newTime.setText("9:23");
        viewHolder.readPerson.setText("123 ");

        GlideApp.with(context)
                .load(R.drawable.home_top_left_pic)
                .error(R.drawable.home_top_left_pic)
                .placeholder(R.drawable.home_top_left_pic)
                .into(viewHolder.newsPic);
        return convertView;
    }

    class  ViewHolder {

        private TextView newsTitle;

        private TextView newsState;

        private TextView newTime;

        private TextView readPerson;

        private ImageView newsPic;

        private LinearLayout itemLayout;
    }
}
