package com.zgw.home.model;

import com.zgw.base.model.BaseBean;

import java.util.List;

public class TodayNewsBean extends BaseBean {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TodayNewsBean> getChildList() {
        return childList;
    }

    public void setChildList(List<TodayNewsBean> childList) {
        this.childList = childList;
    }

    private List<TodayNewsBean> childList;


}
