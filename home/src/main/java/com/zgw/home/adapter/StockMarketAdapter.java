package com.zgw.home.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zgw.home.R;

import java.util.List;

public class StockMarketAdapter extends RecyclerView.Adapter<StockMarketAdapter.FishViewHolder> {
    private List<String> data;
    private LayoutInflater inflater;

    private Context context;

    public StockMarketAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FishViewHolder holder = new FishViewHolder(inflater.inflate(
                R.layout.stock_market_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FishViewHolder holder, int position) {
       holder.nameTextView.setText("拉丝材");
       holder.addressTextView.setText("迁安九江");
       holder.stockIndex.setText("Q195");
       holder.steelType.setText("φ8");
       holder.priceTv.setText("4180");
       holder.upAndDown.setText("+20");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<String> pDatas) {
        data = pDatas;
    }

    class FishViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView addressTextView;

        private TextView stockIndex;
        private TextView steelType;
        private TextView priceTv;
        private TextView upAndDown;

        public FishViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            addressTextView = (TextView) view.findViewById(R.id.addressTextView);
            stockIndex = (TextView) view.findViewById(R.id.stockIndex);
            steelType = (TextView) view.findViewById(R.id.steelType);
            priceTv = (TextView) view.findViewById(R.id.priceTv);
            upAndDown = (TextView) view.findViewById(R.id.upAndDown);
        }
    }
}
