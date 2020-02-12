package com.zgw.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgw.home.R;
import com.zgw.home.model.TodayNewsBean;

import java.util.List;

public class TodayNewsAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<TodayNewsBean> dataList;

    public TodayNewsAdapter(Context mContext) {
        inflater = LayoutInflater.from(mContext);
    }

    public void setDataList(List<TodayNewsBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getGroupCount() {
        return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.today_news_item_title_layout, parent,false);
            groupViewHolder.parent_title = convertView.findViewById(R.id.titleTv);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.parent_title.setText(dataList.get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.today_news_item_layout, parent, false);
            childViewHolder.children_content = convertView.findViewById(R.id.contentTv);
            childViewHolder.forwardLayout = convertView.findViewById(R.id.forwardLayout);
            childViewHolder.timeTv = convertView.findViewById(R.id.timeTv);
            childViewHolder.lineView = convertView.findViewById(R.id.lineView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        if (childPosition == dataList.get(groupPosition).getChildList().size() - 1) {
            childViewHolder.lineView.setVisibility(View.GONE);
        } else {
            childViewHolder.lineView.setVisibility(View.VISIBLE);
        }
        childViewHolder.children_content.setText(dataList.get(groupPosition).getChildList().get(childPosition).getTitle());
        childViewHolder.forwardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.shareLinkUrl("开始转发啦！！！！");
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView parent_title;
    }

    class ChildViewHolder {
        TextView children_content;
        TextView timeTv;
        RelativeLayout forwardLayout;
        View lineView;

    }

    private ForwardListener listener;

    public interface ForwardListener {
        void shareLinkUrl(String url);
    }

    public void addShareListener(ForwardListener listener) {
        this.listener = listener;
    }
}
