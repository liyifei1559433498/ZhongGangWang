package com.zgw.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.JRBJAdapter;
import com.zgw.home.model.JRBJBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LastHangQingView extends FrameLayout {

    private Context mContext;

    @BindView(R2.id.gridView)
    GridView gridView;

    private List<JRBJBean> dataList = new ArrayList<>();

    public static LastHangQingView newInstance(Context context) {
        LastHangQingView fragment = new LastHangQingView(context);
        return fragment;
    }

    public LastHangQingView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public LastHangQingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public LastHangQingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

   private void initView() {
       View view = LayoutInflater.from(mContext).inflate(R.layout.last_hangqing_layout, this, true);
       ButterKnife.bind(this, view);
       initData();
       JRBJAdapter jrbjAdapter = new JRBJAdapter(mContext);
       jrbjAdapter.setDataList(dataList);
       gridView.setAdapter(jrbjAdapter);

   }

   private void initData() {
       for (int i = 0; i < 2; i++) {
           JRBJBean jrbjBean = new JRBJBean();
           jrbjBean.setTitleType("卷板");
           jrbjBean.setSubtitle("低合金板");
           dataList.add(jrbjBean);
       }
   }
}
