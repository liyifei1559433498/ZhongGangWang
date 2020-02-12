package com.zgw.home.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.zgw.home.R;
import com.zgw.home.R2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineChartView extends FrameLayout {

    private Context context;

    @BindView(R2.id.mLineChart)
    LineChart mChart;

    public static LineChartView newInstance(Context context) {
        LineChartView lineChartView = new LineChartView(context);
        lineChartView.setData();
        return lineChartView;
    }

    public LineChartView(Context context) {
        this(context, null);
        this.context = context;
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.linchart_layout, this, true);
        ButterKnife.bind(this, view);
    }

    private void setData() {
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);//背景巨型
        mChart.setHighlightPerDragEnabled(true);
        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);


        ArrayList<Entry> dataList1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        ArrayList<Entry> yVals3 = new ArrayList<Entry>();
        for (int i = 0; i < 12; i++) {
            dataList1.add(new Entry(i, new Random().nextInt(100)));
            yVals2.add(new Entry(i, new Random().nextInt(100)));
            yVals3.add(new Entry(i, new Random().nextInt(100)));
        }

        YAxis rightAxis = mChart.getAxisRight();
        //设置图表右边的y轴禁用// set an alternative background color
        rightAxis.setEnabled(false);
        YAxis leftAxis = mChart.getAxisLeft();
        //设置图表左边的y轴禁用mChart.setBackgroundColor(Color.WHITE);
        leftAxis.setEnabled(true);
        leftAxis.setAxisMaximum(100);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(0);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1);//禁止放大x轴标签重绘
        xAxis.setCenterAxisLabels(true);
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            list.add(String.valueOf(i).concat("月"));
        }
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setLabelCount(list.size() + 1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        xAxis.setYOffset(1f);
        xAxis.setCenterAxisLabels(true);


        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(dataList1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(dataList1, "上海");
            set1.setColor(Color.parseColor("#54A1FF"));
            set1.setCircleColor(Color.parseColor("#54A1FF"));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setHighLightColor(Color.parseColor("#54A1FF"));
            set1.setDrawCircleHole(false);


            set2 = new LineDataSet(yVals2, "北京");
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.RED);

            set3 = new LineDataSet(yVals3, "广州");
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.YELLOW);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.YELLOW);


            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            mChart.setData(data);
        }
    }
}
