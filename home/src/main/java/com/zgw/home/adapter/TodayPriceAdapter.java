package com.zgw.home.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.zgw.base.component.BarChartManager;
import com.zgw.home.R;

import java.util.ArrayList;
import java.util.List;

public class TodayPriceAdapter extends RecyclerView.Adapter<TodayPriceAdapter.FishViewHolder> {

    private Context context;

    public TodayPriceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.today_price_item_layout, parent, false);

        FishViewHolder holder = new FishViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FishViewHolder holder, final int position) {

        BarChartManager barChartManager = new BarChartManager(holder.barChat, context);
        List<BarEntry> yVals = new ArrayList<>();
        BarEntry barEntry = new BarEntry(1f, 3950f);
        barEntry.setIcon(context.getResources().getDrawable((R.drawable.price_up_icon)));
        yVals.add(barEntry);
        yVals.add(new BarEntry(2f, 3950f));
        yVals.add(new BarEntry(3f, 4000f));
        yVals.add(new BarEntry(4f, 4200));
        yVals.add(new BarEntry(5f, 4200));
        yVals.add(new BarEntry(6f, 4300));
        String label = "";
        barChartManager.showBarChart(yVals, label, Color.parseColor("#233454"));

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class FishViewHolder extends RecyclerView.ViewHolder {

        private BarChart  barChat;

        public FishViewHolder(View view) {
            super(view);
            barChat = (BarChart) view.findViewById(R.id.barChat);
        }
    }
}
