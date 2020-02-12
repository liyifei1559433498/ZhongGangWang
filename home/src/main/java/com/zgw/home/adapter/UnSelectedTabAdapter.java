package com.zgw.home.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zgw.home.R;
import com.zgw.home.model.NewsCategoryBean;

import java.util.List;

public class UnSelectedTabAdapter extends RecyclerView.Adapter {

    public List<NewsCategoryBean> dataList;

    private Context context;

    public UnSelectedTabAdapter(Context context, List<NewsCategoryBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setDataList(List<NewsCategoryBean> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UnTabViewHolder(LayoutInflater.from(context).inflate(R.layout.add_item_news_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsCategoryBean item = dataList.get(position);
        UnTabViewHolder tabViewHolder = (UnTabViewHolder) holder;
        tabViewHolder.mViewTab.setText(item.getTitle());
        tabViewHolder.mViewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTabListener.addTabListener(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }


    class UnTabViewHolder extends RecyclerView.ViewHolder {


        public TextView mViewTab;


        UnTabViewHolder(View view) {
            super(view);
            mViewTab = (TextView) view.findViewById(R.id.tv_content);

            mViewTab.setTextColor(new ColorStateList(new int[][]{
                            new int[]{android.R.attr.state_selected},
                            new int[]{-android.R.attr.state_selected}
                    }, new int[]{0XFFFF7600, 0XFF454545})
            );
            mViewTab.setActivated(true);

            mViewTab.setTag(this);


        }
    }

    private AddTabListener addTabListener;

    public void setAddTabListener(AddTabListener addTabListener) {
        this.addTabListener = addTabListener;
    }

    public interface AddTabListener {
        void addTabListener(int position, NewsCategoryBean newsCategoryBean);
    }
}
