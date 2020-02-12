package com.zgw.base.component.pricemap;

import android.graphics.Path;
import android.graphics.Region;

import java.util.List;


public class ProvinceModel {
    private String name;

    private int color;

    private int linecolor;

    private List<Path> listpath;

    private List<Region> regionList;

    private boolean isSelect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public int getLinecolor() {
        return linecolor;
    }

    public void setLinecolor(int linecolor) {
        this.linecolor = linecolor;
    }

    public List<Path> getListpath() {
        return listpath;
    }

    public void setListpath(List<Path> listpath) {
        this.listpath = listpath;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
