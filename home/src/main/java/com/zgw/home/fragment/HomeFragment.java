package com.zgw.home.fragment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.cache.RWSharedPreferences;
import com.zgw.base.runtimepermissions.PermissionsManager;
import com.zgw.base.runtimepermissions.PermissionsResultAction;
import com.zgw.base.ui.BaseFragment;
import com.zgw.base.util.Constants;
import com.zgw.base.util.LogUtil;
import com.zgw.base.util.ZGWToast;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.activity.SelectedCityActivity;
import com.zgw.home.adapter.TabFragmentAdapter;
import com.zgw.home.model.NewsCategoryBean;
import com.zgw.home.tablayout.FileCacheConstants;
import com.zgw.home.tablayout.FileUtils;
import com.zgw.home.tablayout.ShellRWConstants;
import com.zgw.home.tablayout.SmartTabLayout;
import com.zgw.home.tablayout.TabManager;
import com.zgw.home.tablayout.TabPickView;
import com.zgw.location.LocationHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment implements View.OnClickListener, LocationHelper.LocationCallBack {

    private int[] textSelectColor = new int[2];// tab表头选中字体颜色

    private int divideColor;

    private List<NewsCategoryBean> tabs;

    private List<NewsCategoryBean> unTabs = new ArrayList<>();

    @BindView(R2.id.tabLayout)
    SmartTabLayout mTabLayout;

    @BindView(R2.id.viewpager)
    ViewPager mViewPager;

    @BindView(R2.id.tab_picker)
    TabPickView mTabPicker;

    @BindView(R2.id.iv_arrow_down)
    ImageView ivArrowDown;

    private int mSelectedIndex = 0;

    private TabPickView.TabPickerDataManager mTabPickerDataManager;

    private RWSharedPreferences rwSharedPreferences;

    private TabFragmentAdapter adapter;

    private List<Fragment> fragmentList = new ArrayList<>();

    @BindView(R2.id.fl_arrow_down)
    FrameLayout fl_arrow_down;

    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @BindView(R2.id.locationBtn)
    TextView locationBtn;

    @BindView(R2.id.searchLayout)
    RelativeLayout searchLayout;

    private LocationHelper locationHelper;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE};

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_layout, container, false);
        ButterKnife.bind(this, view);
        requestPermissions();
        getLocationData();
        initView();
        initTabLayout();
        return view;

    }

    private void requestPermissions() {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
                LogUtil.outLog("TAG", "permission == " + permission);
            }
        });
    }

    private void initView() {
        locationBtn.setOnClickListener(this);
        ImmersionBar.setTitleBar(this, toolbar);
        rwSharedPreferences = new RWSharedPreferences(getContext(), "homeTab");
        this.textSelectColor[0] = getResources().getColor(R.color.text_gray);
        this.textSelectColor[1] = getResources().getColor(R.color.orange);
        fl_arrow_down.setOnClickListener(this);
    }

    /**
     * 获取位置
     */
    private void getLocationData() {
        locationHelper = LocationHelper.getInstance(getContext());
        locationHelper.setCallBack(this);
        locationHelper.start();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        if (divideColor == 0) {
            divideColor = ContextCompat.getColor(getContext(), R.color.orange);
        }

        String data = FileUtils.readDataFromLocal(getContext(), FileCacheConstants.CACHE_NAME_LOTTERY_NEWS);
        if (TextUtils.isEmpty(data)) {
            data = FileUtils.getassetsFile("news_category.txt", getContext());
        }
        //初始化数据
        tabs = JSON.parseArray(data, NewsCategoryBean.class);
        Collections.sort(tabs, new Comparator<NewsCategoryBean>() {
            @Override
            public int compare(NewsCategoryBean o1, NewsCategoryBean o2) {
                if (o1.getMove() < o2.getMove()) {
                    return -1;
                } else if (o1.getMove() >= o2.getMove()) {
                    return 1;
                }
                return 0;
            }
        });

        List<NewsCategoryBean> tabList = TabManager.getTabList(getContext());

        unTabs = TabManager.getDiff(tabList, tabs);

        for (int i = 0; i < tabs.size(); i++) {
            fragmentList.add(TabManager.fragmentMap.get(tabs.get(i).getCategoryId()));
        }

        adapter = new TabFragmentAdapter(getChildFragmentManager(), tabs, fragmentList);
        mTabLayout.setIndicatorWithoutPadding(true);
        mTabLayout.setDefaultTabTextColor(textSelectColor[0]);
        mTabLayout.setSelectTabTextColor(textSelectColor[1]);
        mTabLayout.setTabTextSize(14);
        setTabDivide(false, divideColor);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mTabPicker.setTabPickerManager(initTabPickerManager());
        mTabPicker.setOnTabPickingListener(new TabPickView.OnTabPickingListener() {

            private boolean isChangeIndex = false;

            @Override
            @SuppressWarnings("all")
            public void onSelected(final int position) {
                final int index = mViewPager.getCurrentItem();
                if (position != index) {
                    // notifyDataSetChanged会导致TabLayout位置偏移，而且需要延迟设置才能起效？？？
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(position);
                            mSelectedIndex = position;
                        }
                    }, 50);
                }
            }

            @Override
            public void onRemove(int position, NewsCategoryBean tab) {
                isChangeIndex = true;

            }

            @Override
            public void onInsert(NewsCategoryBean tab) {
                isChangeIndex = true;
                for (int i = 0; i < unTabs.size(); i++) {
                    if (unTabs.get(i).getCategoryId().equals(tab.getCategoryId())) {
                        unTabs.remove(tab);
                    }
                }
                tabs.add(tab);
            }

            @Override
            public void onMove(int op, int np) {
                isChangeIndex = true;
            }

            @Override
            public void onRestore(final List<NewsCategoryBean> mActiveDataSet) {
                if (!isChangeIndex) return;
                isChangeIndex = false;
                tabs = mActiveDataSet;
                adapter.notifyDataSetChanged();
                mTabLayout.setViewPager(mViewPager);

                rwSharedPreferences.putValue(ShellRWConstants.NEWS_IS_SORT, true);
                FileUtils.saveDataToLocal(getContext(), JSON.toJSONString(tabs),
                        FileCacheConstants.CACHE_NAME_LOTTERY_NEWS);
                StringBuilder builder = new StringBuilder();
                for (NewsCategoryBean bean : tabs) {
                    builder.append(bean.getCategoryId());
                    builder.append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
//                lotteryNewsCategoryService.saveNewsCategorySort(builder.toString(), userNo);//网络请求
            }
        });

        mTabPicker.setOnShowAnimation(new TabPickView.AnimAction<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator animator) {
                showArrowDownAnimation(45, 120);
            }
        });

        mTabPicker.setOnHideAnimator(new TabPickView.AnimAction<ViewPropertyAnimator>() {
            @Override
            public void call(ViewPropertyAnimator animator) {
                showArrowDownAnimation(0, 120);
            }
        });
    }

    private void setTabDivide(boolean isShowDivide, int color) {
        if (isShowDivide) {
            mTabLayout.setDividerColors(color);
        } else {
            mTabLayout.setDividerColors(ContextCompat.getColor(getContext(), R.color.transparent));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fl_arrow_down) {
            if (mTabPicker.isShow()) {
                mTabPicker.hide();
            } else {
                mTabPicker.show(mSelectedIndex);
            }
        } else if (id == R.id.locationBtn) {
            Intent intent = new Intent(getContext(), SelectedCityActivity.class);
            ((Activity) getContext()).startActivityForResult(intent, 10001);
        }

    }

    @Override
    public void locationCallBack(String province, String city, String district, double latitude, double longitude, int locType) {
        if (62 == locType) {
            ZGWToast.show(getContext(), "定位失败，请检查是否开启定位权限");
        } else {
            if (!TextUtils.isEmpty(city)) {
                Constants.province = province;
                Constants.currentCity = city;
                Constants.district = district;
            }
            locationBtn.setText(Constants.currentCity);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(final int index) {
            mSelectedIndex = index;
        }

        @Override
        public void onPageScrolled(int index, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    }

    private TabPickView.TabPickerDataManager initTabPickerManager() {
        if (mTabPickerDataManager == null) {
            mTabPickerDataManager = new TabPickView.TabPickerDataManager() {
                @Override
                public List<NewsCategoryBean> setupActiveDataSet() {
                    return tabs;
                }

                @Override
                public List<NewsCategoryBean> setSelectedList() {
                    return unTabs;
                }

                @Override
                public List<NewsCategoryBean> setupOriginalDataSet() {
                    return null;
                }

                @Override
                public void restoreActiveDataSet(List<NewsCategoryBean> mActiveDataSet) {

                }

                @Override
                public void unRestoreActiveDataSet(List<NewsCategoryBean> unSelectedList) {
                }
            };
        }
        return mTabPickerDataManager;
    }

    private void showArrowDownAnimation(final float rotation, int duration) {
        ivArrowDown.setEnabled(false);
        ivArrowDown.animate()
                .rotation(rotation)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        ivArrowDown.setRotation(rotation);
                        ivArrowDown.setEnabled(true);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if ("android.permission.ACCESS_COARSE_LOCATION".equals(permissions[i]) && (grantResults[i] == 0)) {
                locationHelper.restart();
            }
        }
    }
}