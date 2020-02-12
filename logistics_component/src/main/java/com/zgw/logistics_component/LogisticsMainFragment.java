package com.zgw.logistics_component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.billy.cc.core.component.CC;
import com.gyf.immersionbar.ImmersionBar;
import com.zgw.logistics_component.adapter.AdapterOfBigRoom;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 物流界面
 **/
public class LogisticsMainFragment extends Fragment implements View.OnClickListener {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerview_of_logistic)
    RecyclerView recyclerview_of_logistic;
//
//    @BindView(R2.id.msgBtn)
//    TextView msgBtn;

    public static LogisticsMainFragment newInstance() {
        Bundle args = new Bundle();
        LogisticsMainFragment fragment = new LogisticsMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logistics_main_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdapterOfBigRoom adapterOfBigRoom = new AdapterOfBigRoom(getActivity());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerview_of_logistic.setLayoutManager(linearLayoutManager);


        recyclerview_of_logistic.setAdapter(adapterOfBigRoom);
    }

    private void initView() {
        ImmersionBar.setTitleBar(this, toolbar);

//        msgBtn.setOnClickListener(this);
    }


    //消息已读的时候调用消除底部tab消息个数
    public void refreshMsg() {
        CC.obtainBuilder("componentApp")
                .setActionName("refreshMsg")
                .addParam("msgCount", "1111")
                .build().call();
    }

    @Override
    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.msgBtn) {
//            refreshMsg();
//        }
    }
}
