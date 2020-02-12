package com.zgw.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zgw.home.R;
import com.zgw.home.adapter.JRBJDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class JRBJDetailFragment extends Fragment {

    private ListView listView;

    public static JRBJDetailFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        JRBJDetailFragment fragment = new JRBJDetailFragment();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jrbj_detail_fragment, container, false);
        listView = view.findViewById(R.id.detailListView);
        initView();
        return view;
    }

    private void initView() {
        JRBJDetailAdapter adapter = new JRBJDetailAdapter(getContext());
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("qqq");
        }
        adapter.setDataList(dataList);
        listView.setAdapter(adapter);

    }
}
