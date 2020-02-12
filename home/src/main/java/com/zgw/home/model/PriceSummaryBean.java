package com.zgw.home.model;

import com.zgw.base.model.BaseBean;

/**
 * Created by Administrator on 2019/10/8 0008.
 */

public class PriceSummaryBean extends BaseBean {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;
}
