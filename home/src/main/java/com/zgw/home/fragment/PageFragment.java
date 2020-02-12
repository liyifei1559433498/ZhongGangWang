package com.zgw.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.zgw.home.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class PageFragment extends Fragment {

    protected final static String title = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static PageFragment newInstance(String channelId, String channelName) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(title, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        LineChart mChart = view.findViewById(R.id.mLineChart);
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
        return view;
    }

}
