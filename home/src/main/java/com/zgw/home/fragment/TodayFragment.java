package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zgw.home.R;
import com.zgw.home.adapter.TodayPriceAdapter;


/**
 * Created by Administrator on 2019/9/24 0024.
 */

public class TodayFragment extends Fragment {

    private String mChannelId = "";
    private String mChannelName = "";

    protected final static String title = "title";
    protected final static String data = "data";

    private RecyclerView recycleView;

    private TodayPriceAdapter adapter;

    public static TodayFragment newInstance(String channelId, String channelName) {
        TodayFragment fragment = new TodayFragment();
        Bundle bundle = new Bundle();
        bundle.putString(title, channelId);
        bundle.putString(data, channelName);
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
        View view = inflater.inflate(R.layout.today_fragment_layout, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            adapter = new TodayPriceAdapter(getActivity());
            recycleView.setLayoutManager(layoutManager);
            recycleView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        return view;

    }
}
