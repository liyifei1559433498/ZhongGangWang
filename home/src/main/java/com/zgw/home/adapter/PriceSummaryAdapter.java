package com.zgw.home.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zgw.home.R;
import com.zgw.home.activity.PriceSummaryActivity;
import com.zgw.home.activity.PriceSummaryDetailActivity;
import com.zgw.home.model.PriceSummaryBean;
import com.zgw.base.component.ShapeBackGroundView;

import java.util.List;

public class PriceSummaryAdapter extends RecyclerView.Adapter<PriceSummaryAdapter.ViewHolder> {

    private List<PriceSummaryBean> dataList;

    private Context context;

    public PriceSummaryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.price_summary_item_layout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.icon.setBackgroundResource(dataList.get(position).getIcon());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PriceSummaryDetailActivity.class);
                intent.putExtra("title", dataList.get(position).getTitle());
                ((PriceSummaryActivity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<PriceSummaryBean> pDatas) {
        dataList = pDatas;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private ShapeBackGroundView itemLayout;

        private ImageView icon;

        public ViewHolder(View view) {
            super(view);
            itemLayout = (ShapeBackGroundView) view.findViewById(R.id.itemLayout);
            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
