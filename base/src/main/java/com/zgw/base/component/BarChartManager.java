package com.zgw.base.component;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.zgw.base.util.PublicViewUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BarChartManager {
    private BarChart mBarChart;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    private DecimalFormat mFormat;
    private Context context;

    public BarChartManager(BarChart barChart, Context context) {
        this.mBarChart = barChart;
        leftAxis = mBarChart.getAxisLeft();
        rightAxis = mBarChart.getAxisRight();
        xAxis = mBarChart.getXAxis();
        this.context = context;
    }

    /**
     * 展示柱状图(一条)
     */
    public void showBarChart(List<BarEntry> yVals, String label, int color) {
//        initLineChart();

        mBarChart.setDrawBorders(false);
        mBarChart.setDragEnabled(false);
        mBarChart.setScaleEnabled(false);
        //设置XY动画效果
//        mBarChart.animateY(1000, Easing.EasingOption.Linear);
//        mBarChart.animateX(1000, Easing.EasingOption.Linear);
//      不显示描述信息
        mBarChart.getDescription().setEnabled(false);
        Legend l = mBarChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//      X轴最小间距
        xAxis.setGranularity(1f);
//      不绘制网格线
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
//      X轴字体样式
//      设置X轴文字剧中对齐
        xAxis.setCenterAxisLabels(true);
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setAxisMaximum(4200f);
        leftAxis.setDrawZeroLine(false);
//        // 线跟数据都不显示
        rightAxis.setEnabled(false); //右侧Y轴不显示






        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(yVals, label);
        barDataSet.setColor(color);

        barDataSet.setDrawIcons(true);
        MPPointF mpPointF = new MPPointF(PublicViewUtils.getPxInt(3,context),-PublicViewUtils.getPxInt(2,context));
        barDataSet.setIconsOffset(mpPointF);
        //是否显示顶部的值
        barDataSet.setDrawValues(true);
//        文字的大小
        barDataSet.setValueTextSize(9f);

        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.0f);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
//      设置宽度
        data.setBarWidth(0.3f);
        //设置X轴的刻度数
        String[] xValues = {"北京", "上海", "广州", "重庆", "郑州", "武汉", "香港"};
//        String[] yValues = {"91%", "92%", "93%", "94%", "95%", "96%"};
        xAxis.setLabelCount(yVals.size() + 1, true);
        xAxis.setDrawLabels(true);

        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextColor(Color.parseColor("#d5d5d5"));
        xAxis.setAxisLineColor(Color.parseColor("#d5d5d5"));
//        IAxisValueFormatter custom = new MyYAxisValueFormatter(yValues);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setLabelCount(yValues.length + 1, false);
        leftAxis.setAxisLineColor(Color.parseColor("#000000"));
//        设置Y轴的最小值和最大值
//        leftAxis.setAxisMaximum(4100f);
        leftAxis.setAxisMinimum(3800);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        mBarChart.setData(data);
    }

    public class MyYAxisValueFormatter implements IAxisValueFormatter {

        private String[] xValues;

        public MyYAxisValueFormatter(String[] yValues) {
            xValues = yValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            Log.e("TAG", "xValues[(int) value]====="+xValues[(int) value]);
            return mFormat.format(value) + "%";
        }
    }

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] xValues;

        public XAxisValueFormatter(String[] xValues) {
            this.xValues = xValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xValues[(int) value];
        }

    }
}
