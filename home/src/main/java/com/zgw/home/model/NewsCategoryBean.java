package com.zgw.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zgw.base.model.BaseBean;

/**
 * tab数据
 */

public class NewsCategoryBean extends BaseBean {

    private String categoryId;

    private String title;

    private String type;

    private int sort;

    private int move;

    private String url;

    private int isActived;

    private int showFlag;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int isActived() {
        return isActived;
    }

    public void setActived(int actived) {
        isActived = actived;
    }

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeString(categoryId);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeInt(move);
        dest.writeInt(sort);
        dest.writeInt(isActived);
        dest.writeInt(showFlag);
    }
    public static final Parcelable.Creator<NewsCategoryBean> CREATOR = new Parcelable.Creator<NewsCategoryBean>() {

        @Override
        public NewsCategoryBean createFromParcel(Parcel source) {
            NewsCategoryBean bean = new NewsCategoryBean();
            bean.categoryId = source.readString();
            bean.title = source.readString();
            bean.type = source.readString();
            bean.url = source.readString();
            bean.move = source.readInt();
            bean.sort = source.readInt();
            bean.isActived = source.readInt();
            bean.showFlag = source.readInt();
            return bean;
        }

        @Override
        public NewsCategoryBean[] newArray(int size) {
            return new NewsCategoryBean[size];
        }
    };
}
