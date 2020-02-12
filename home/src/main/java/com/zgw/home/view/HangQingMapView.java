package com.zgw.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zgw.base.component.pricemap.ChinaMapModel;
import com.zgw.base.component.pricemap.ChinaMapView;
import com.zgw.base.component.pricemap.ColorChangeUtil;
import com.zgw.base.component.pricemap.SvgUtil;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HangQingMapView extends FrameLayout {

    private Context mContext;

    @BindView(R2.id.hangQingMap)
    ChinaMapView mapView;

//    private provinceAdapter adapter;

    private ChinaMapModel myMap;

    public static HangQingMapView newInstance(Context context) {
        HangQingMapView hangQingMapView = new HangQingMapView(context);
        return hangQingMapView;
    }

    public HangQingMapView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public HangQingMapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public HangQingMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hangqing_map_layout, this, true);
        ButterKnife.bind(this, view);

        initMap();
        //初始化map各省份颜色
        ColorChangeUtil.changeMapColors(myMap, ColorChangeUtil.nameStrings[0]);
//        headerView.setData("60+");
        mapView.chingeMapColors();

        mapView.setOnChoseProvince(new ChinaMapView.onProvinceClickLisener() {
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

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(mContext).getProvinces();

        //传数据
        mapView.setMap(myMap);
    }

}
