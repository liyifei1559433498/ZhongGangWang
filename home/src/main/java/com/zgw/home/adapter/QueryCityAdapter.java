package com.zgw.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zgw.home.R;
import com.zgw.home.model.City;
import java.util.List;

public class QueryCityAdapter extends BaseAdapter {

    private List<City> dataList;

    private LayoutInflater layoutInflater;

    public QueryCityAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setQueryList(List<City> dataList) {
        this.dataList = dataList;
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
            convertView = layoutInflater.inflate(R.layout.query_city_item_layout, null);
            viewHolder.cityName = convertView.findViewById(R.id.cityName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cityName.setText(dataList.get(position).getName());
        return convertView;
    }


    class  ViewHolder {

        private TextView cityName;

    }
}
