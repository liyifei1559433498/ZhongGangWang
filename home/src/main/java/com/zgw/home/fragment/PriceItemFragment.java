package com.zgw.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.zgw.base.component.BarChartManager;
import com.zgw.home.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/10/8 0008.
 */

public class PriceItemFragment extends Fragment {

    protected final static String TITLE = "title";

    protected final static String URL = "url";

    private BarChart barChart;

    public static PriceItemFragment newInstance(String channelId, String channelName) {
        PriceItemFragment fragment = new PriceItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, channelId);
        bundle.putString(URL, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BarChartManager barChartManager = new BarChartManager(barChart, getActivity());
        List<BarEntry> yVals = new ArrayList<>();
        BarEntry barEntry = new BarEntry(1f, 3950f);
        barEntry.setIcon(getActivity().getResources().getDrawable((R.drawable.price_up_icon)));
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.price_item_fragment_layout, container, false);
        barChart = view.findViewById(R.id.barChat);
        return view;
    }
}
