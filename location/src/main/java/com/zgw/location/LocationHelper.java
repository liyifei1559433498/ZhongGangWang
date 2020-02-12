package com.zgw.location;

import android.content.Context;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationHelper {

    public LocationCallBack callBack;

    private static LocationHelper helper;

    private LocationClient locationClient;

    private BDAbstractLocationListener locationListener = new MyBDLocationListener();

    private Context context;

    private LocationHelper(Context context) {
        this.context = context;
        //第一步实例化定位核心类
        locationClient = new LocationClient(context.getApplicationContext(), getLocOption());
        //第二步设置位置变化回调监听
        locationClient.registerLocationListener(locationListener);
    }

    public static LocationHelper getInstance(Context context) {
        if (helper == null) {
            helper = new LocationHelper(context);
        }
        return helper;
    }

    //第三步开始定位
    public void start() {
        if (locationClient == null) {
            locationClient = new LocationClient(context.getApplicationContext(), getLocOption());
            //第二步设置位置变化回调监听
            locationClient.registerLocationListener(locationListener);
        }
        locationClient.start();
    }

    public void restart() {
        if (locationClient != null && !locationClient.isStarted()) {
            locationClient.restart();
        }
    }

    //一般会在Activity的OnDestroy方法调用
    public void stop() {
        if (locationClient != null) {
            locationClient.unRegisterLocationListener(locationListener);
            locationClient.stop();
            locationClient = null;
        }
    }

    //停止
    public void locationClientStop() {
        if (locationClient != null) {
            locationClient.stop();
        }
    }

    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll"); //可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true); //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true); //可选，默认false,设置是否使用gps
        option.setLocationNotify(true); //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true); //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false); //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false); //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false); //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false); //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        return option;
    }

    private class MyBDLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (callBack != null && bdLocation != null) {
                String province = bdLocation.getProvince();
                String city = bdLocation.getCity();
                String district = bdLocation.getDistrict();
                double latitude = bdLocation.getLatitude();
                double longitude = bdLocation.getLongitude();
                int locType = bdLocation.getLocType();
                callBack.locationCallBack(province, city, district, latitude, longitude, locType);
            }
            //多次定位必须要调用stop方法
            locationClient.stop();
        }
    }

    public interface LocationCallBack {
        void locationCallBack(String province, String city, String district , double latitude, double longitude, int locType);
    }

    public LocationCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(LocationCallBack callBack) {
        this.callBack = callBack;
    }
}
