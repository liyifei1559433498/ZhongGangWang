package com.zgw.home.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.component.pricemap.ChinaMapModel;
import com.zgw.base.component.pricemap.ChinaMapView;
import com.zgw.base.component.pricemap.ColorChangeUtil;
import com.zgw.base.component.pricemap.MycolorArea;
import com.zgw.base.component.pricemap.SvgUtil;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.R2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/9/29 0029.
 */

public class StockMapActivity extends BaseActivity {

    @BindView(R2.id.view)
    ChinaMapView mapview;

//    private provinceAdapter adapter;

    private ChinaMapModel myMap;

//    @BindView(R2.id.changeType)
//    Button changeType;

//    @BindView(R2.id.colorView)
//    ColorView colorView;

    private int currentColor = 0;

//    @BindView(R2.id.province_listview)
//    ListView province_listView;

    private HashMap<String, List<MycolorArea>> colorView_hashmap;

    private List<String> list;

//    @BindView(R2.id.headerView)
//    MapHeaderView headerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_map_layout);
        ButterKnife.bind(this);
        initView();
        //设置颜色渐变条
//        setColorView();
        //初始化map
        initMap();
        //初始化map各省份颜色
        ColorChangeUtil.changeMapColors(myMap, ColorChangeUtil.nameStrings[0]);
//        headerView.setData("60+");
        mapview.chingeMapColors();
        //listview
        setListAdapter();
//        changeType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String namestring = ColorChangeUtil.nameStrings[++currentColor % ColorChangeUtil.nameStrings.length];
//                LogUtil.outLog("TAG", "namestring == " + namestring);
//                changeType.setText(namestring);
//                colorView.setList(colorView_hashmap.get(namestring));
//                //重置map各省份颜色
//                ColorChangeUtil.changeMapColors(myMap, namestring);
//                mapview.chingeMapColors();
//            }
//        });
        mapview.setOnChoseProvince(new ChinaMapView.onProvinceClickLisener() {
            @Override
            public void onChose(String provincename) {
                //地图点击省份回调接口，listview滚动到相应省份位置
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).contains(provincename)) {
//                        adapter.setPosition(i);
//                        province_listView.setSelection(i);
//                        break;
//                    }
//                }

                LogUtil.outLog("TAG", "provincename == " + provincename);
            }
        });
    }
    private void setListAdapter() {
        list=new ArrayList<>();
        //最后三个是香港，澳门和台湾，不需要
        for (int i = 0; i < ColorChangeUtil.province_datas.length - 3; i++){
            list.add(ColorChangeUtil.province_datas[i]);
        }
//        adapter = new provinceAdapter(this, list);
//        province_listView.setAdapter(adapter);
    }

    private void initView() {
        toolbar = $(R.id.toolbar);
        initParameter();
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
//        changeType.setFocusable(true);
//        changeType.setFocusableInTouchMode(true);
//        changeType.setVisibility(View.GONE);
//        changeType.setText(ColorChangeUtil.nameStrings[0]);
    }
    /**
     * 设置颜色渐变条
     */
    private void setColorView() {
//        colorView_hashmap = new HashMap<>();
//        for (int i = 0; i < ColorChangeUtil.nameStrings.length; i++) {
//            String colors[] = ColorChangeUtil.colorStrings[i].split(",");
//            String texts[] = ColorChangeUtil.textStrings[i].split(",");
//            List<MycolorArea> list = new ArrayList<>();
//            for (int j = 0; j < colors.length; j++) {
//                MycolorArea c = new MycolorArea();
//                c.setColor(Color.parseColor(colors[j]));
//                c.setText(texts[j]);
//                list.add(c);
//            }
//            colorView_hashmap.put(ColorChangeUtil.nameStrings[i], list);
//        }
//        colorView.setList(colorView_hashmap.get(ColorChangeUtil.nameStrings[0]));
    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(this).getProvinces();

        //传数据
        mapview.setMap(myMap);
    }
}
