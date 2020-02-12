package com.zgw.home.tablayout;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.zgw.home.fragment.CGDTFragment;
import com.zgw.home.fragment.CJXWFragment;
import com.zgw.home.fragment.GCDSFragment;
import com.zgw.home.fragment.HGJJFragment;
import com.zgw.home.fragment.HYJJFragment;
import com.zgw.home.fragment.HangQingFragment;
import com.zgw.home.fragment.JRTJFragment;
import com.zgw.home.fragment.RDZZFragment;
import com.zgw.home.fragment.TotalTabFragment;
import com.zgw.home.fragment.XianTongHuoFragment;
import com.zgw.home.fragment.YXSJFragment;
import com.zgw.home.fragment.YZXHFragment;
import com.zgw.home.fragment.ZhaoHuoFragment;
import com.zgw.home.fragment.ZiXunFragment;
import com.zgw.home.model.HangQingTabBean;
import com.zgw.home.model.NewsCategoryBean;
import com.zgw.home.view.HangQingMapView;
import com.zgw.home.view.LastHangQingView;
import com.zgw.home.view.TodayNewsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabManager {

    public static HashMap<String, Fragment> fragmentMap = new HashMap<String, Fragment>() {
        {
            put("zh", TotalTabFragment.newInstance());
            put("hq", HangQingFragment.newInstance("title == 行情"));
            put("zx", ZiXunFragment.newInstance("title == 资讯"));
            put("xth", XianTongHuoFragment.newInstance("title == 现通货"));
            put("cgdt", CGDTFragment.newInstance("title == 采购大厅"));
            put("zhaohuo", ZhaoHuoFragment.newInstance("title == 找货"));
            put("yxsj", YXSJFragment.newInstance("title == 优秀商家"));
            put("gcds", GCDSFragment.newInstance("title == 钢厂代售"));
            put("yzxh", YZXHFragment.newInstance("title == 优质现货"));
            put("rdzz", RDZZFragment.newInstance("title == 热点资讯"));
            put("hyjj", HYJJFragment.newInstance("title == 行业聚焦"));
            put("hgjj", HGJJFragment.newInstance("title == 宏观经济"));
            put("cjxw", CJXWFragment.newInstance("title == 产经新闻"));
            put("jrtj", JRTJFragment.newInstance("title == 今日特价"));
        }

    };

    public static List<NewsCategoryBean> getTabList(Context context) {
        List<NewsCategoryBean> tabs = null;
        try {
            String data = FileUtils.getassetsFile("news_category.txt", context);
            tabs = JSON.parseArray(data, NewsCategoryBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabs;
    }

    /**
     * 利用hashMap效率比较高
     */
    public static List<NewsCategoryBean> getDiff(List<NewsCategoryBean> mAllList, List<NewsCategoryBean> mSubList) {

        //第一步：构建mAllList的HashMap
        //将mAllList中的元素作为键，如果不是String类，需要实现hashCode和equals方法
        //将mAllList中的元素对应的位置作为值
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < mAllList.size(); i++) {
            map.put(mAllList.get(i).getCategoryId(), i);
        }
        //第二步：利用map遍历mSubList，查找重复元素
        //把mAllList中所有查到的重复元素的位置置空
        for (int i = 0; i < mSubList.size(); i++) {
            Integer pos = map.get(mSubList.get(i).getCategoryId());
            if (pos == null) {
                continue;
            }
            mAllList.set(pos, null);
        }
        //第三步：把mAllList中所有的空元素移除
        for (int i = mAllList.size() - 1; i >= 0; i--) {
            if (mAllList.get(i) == null || mAllList.get(i).getCategoryId() == null) {
                mAllList.remove(i);
            }
        }

        return mAllList;
    }

    public static View getHangQingView(String id, Context context) {
        View view = null;
        if ("zxhq".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("jrkx".equals(id)) {
            view = TodayNewsView.newInstance(context);
        } else if ("xqdt".equals(id)) {
            view = HangQingMapView.newInstance(context);
        } else if ("jgzs".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("jrbj".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("gshq".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("jghz".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("mrfx".equals(id)) {
            view = LastHangQingView.newInstance(context);
        } else if ("mzpx".equals(id)) {
            view = LastHangQingView.newInstance(context);
        }
        return view;
    }
}
