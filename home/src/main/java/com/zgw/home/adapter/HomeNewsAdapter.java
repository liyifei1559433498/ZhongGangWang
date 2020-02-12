package com.zgw.home.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zgw.base.GlideApp;
import com.zgw.base.util.ZGWToast;
import com.zgw.home.R;

import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.FishViewHolder> {
    private List<String> data;

    private Context context;

    public HomeNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_news_item, null);
        FishViewHolder holder = new FishViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FishViewHolder holder, final int position) {
        holder.newsTitle.setText("22日黑色系TOP20仓单日报：黑色资金");
        holder.newsState.setText("机构点评");
        holder.newTime.setText("9:23");
        holder.readPerson.setText("123  ");
        GlideApp.with(context)
                .load(R.drawable.home_top_left_pic)
                .error(R.drawable.home_top_left_pic)
                .placeholder(R.drawable.home_top_left_pic)
                .into(holder.newsPic);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZGWToast.show(context, "点击了第" + position + "位置");
            }
        });
    }

    @Override
    public int getItemCount() {
        return (data == null || data.size() == 0) ? 0 : data.size();
    }

    public void setData(List<String> pDatas) {
        data = pDatas;
    }

    class FishViewHolder extends RecyclerView.ViewHolder {

        private TextView newsTitle;
        private TextView newsState;
        private TextView newTime;
        private TextView readPerson;
        private ImageView newsPic;
        private LinearLayout itemLayout;

        public FishViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            newsPic = (ImageView) view.findViewById(R.id.newsPic);
            newsState = (TextView) view.findViewById(R.id.newsState);
            newTime = (TextView) view.findViewById(R.id.newTime);
            readPerson = (TextView) view.findViewById(R.id.readPerson);
        }
    }
}
