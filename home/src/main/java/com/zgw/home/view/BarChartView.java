package com.zgw.home.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.zgw.base.component.BarChartManager;
import com.zgw.home.R;
import com.zgw.home.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartView extends FrameLayout {

    private Context context;

    @BindView(R2.id.Bar_chat1)
    BarChart barChart;

    public static BarChartView newInstance(Context context) {
        BarChartView barChartView = new BarChartView(context);
        barChartView.setData();
        return barChartView;
    }

    public BarChartView(Context context) {
        this(context, null);
        this.context = context;
    }

    public BarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.barchart_layout, this, true);
        ButterKnife.bind(this, view);
    }

    public void setData() {
        BarChartManager barChartManager = new BarChartManager(barChart, context);
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
}
