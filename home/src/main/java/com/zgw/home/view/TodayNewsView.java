package com.zgw.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.zgw.base.util.ZGWToast;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.TodayNewsAdapter;
import com.zgw.home.model.TodayNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayNewsView extends FrameLayout {

    private Context mContext;

    @BindView(R2.id.todayNewsListView)
    ExpandableListView todayNewsListView;

    private List<TodayNewsBean> newsList = new ArrayList<>();

    public static TodayNewsView newInstance(Context context) {
        TodayNewsView fragment = new TodayNewsView(context);
        return fragment;
    }

    public TodayNewsView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public TodayNewsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public TodayNewsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.today_news_layout, this, true);
        ButterKnife.bind(this, view);



        for (int i = 0; i < 5; i++) {
            TodayNewsBean todayNewsBean = new TodayNewsBean();
            todayNewsBean.setTitle("2019年05月24日" + (i + 1));
            List<TodayNewsBean> childList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (j == 2) {
                    TodayNewsBean childBean1 = new TodayNewsBean();
                    childBean1.setTitle("24日调价：下午唐山普方坯降20，本地部, 24日调价：下午唐山普方坯降20，本地部, 24日调价：下午唐山普方坯降20，本地部, 24日调价：下午唐山普方坯降20，本地部");
                    childList.add(childBean1);
                } else {
                    TodayNewsBean childBean = new TodayNewsBean();
                    childBean.setTitle("24日调价：下午唐山普方坯降20，本地部");
                    childList.add(childBean);
                }

            }
            todayNewsBean.setChildList(childList);
            newsList.add(todayNewsBean);
        }
        TodayNewsAdapter adapter = new TodayNewsAdapter(getContext());
        adapter.setDataList(newsList);
        todayNewsListView.setAdapter(adapter);

        adapter.addShareListener(new TodayNewsAdapter.ForwardListener() {
            @Override
            public void shareLinkUrl(String url) {
                ZGWToast.show(getContext(), url);
            }
        });
        for (int i = 0; i <newsList.size(); i++) {
            todayNewsListView.expandGroup(i);
        }
//        不能点击收缩
        todayNewsListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }
}
