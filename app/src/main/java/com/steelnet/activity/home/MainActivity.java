package com.steelnet.activity.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.gyf.immersionbar.ImmersionBar;
import com.steelnet.activity.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mmkv.MMKV;
import com.zgw.base.model.RefreshEvent;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.Constants;
import com.zgw.base.util.RxBus;
import com.zgw.base.util.ZGWToast;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;

    private Fragment mHomeFragment;

    private Fragment informationFragment;

    private Fragment personFragment;

    private Fragment logisticsFragment;

    private Fragment lFragment;

    Fragment fromFragment;

    private long backPressed;

    private Disposable refreshDisposable;

    private Badge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initBugly();
        bindRxBusEvent();
        showBadgeView(2,5);//模拟消息个数
    }

    private void initView() {
        ImmersionBar.with(this).init();
        //初始化MMKV缓存框架
        String rootDir = MMKV.initialize(this);
        if (TextUtils.isEmpty(rootDir)) {
            rootDir = getFilesDir().getAbsolutePath() + "/mmkv";
            String initialize = MMKV.initialize(rootDir);
        }

        CCResult result = CC.obtainBuilder("userComponent").setActionName("AccountFragment").build().call();

        CCResult homeResult = CC.obtainBuilder("homeComponent").setActionName("HomeFragment")
                .build().call();

        CCResult loFragment = CC.obtainBuilder("logistics_component").setActionName("LogisticsFragment")
                .build().call();

        CCResult learnFragment = CC.obtainBuilder("learn_component").setActionName("LearnFragment")
                .build().call();

        CCResult infoFragment = CC.obtainBuilder("homeComponent").setActionName("InformationFragment")
                .build().call();

        personFragment = (Fragment) result.getDataMap().get("fragment");//个人中心
        mHomeFragment = (Fragment) homeResult.getDataMap().get("fragment");//首页
        logisticsFragment = (Fragment) loFragment.getDataMap().get("fragment");//物流
        informationFragment = (Fragment) infoFragment.getDataMap().get("fragment");//咨询
        lFragment = (Fragment) learnFragment.getDataMap().get("fragment");//学习

        bottomView.setItemIconTintList(null);
        fromFragment = mHomeFragment;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(bottomView);
        }
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragCategory = null;
                // init corresponding fragment
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragCategory = mHomeFragment;
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
                        break;
                    case R.id.menu_logistics:
                        fragCategory = logisticsFragment;
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
                        break;
                    case R.id.menu_services:
                        fragCategory = informationFragment;
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
                        break;

                    case R.id.menu_learn:
                        fragCategory = lFragment;
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).init();
                        break;
                    case R.id.menu_account:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(true).init();
                        fragCategory = personFragment;
                        break;
                }
                switchFragment(fromFragment, fragCategory);
                fromFragment = fragCategory;
                return true;
            }
        });
        bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mHomeFragment);
        transaction.commit();
//        showBadgeView(3, 5);
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.container, to).commit();
                }

            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commit();
                }

            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                // item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (backPressed + Constants.EXIT_APP_TIME_SPACING > System.currentTimeMillis()) {
                super.onBackPressed();
                moveTaskToBack(true);
            } else {
                ZGWToast.show(this, R.string.exit_app);
                backPressed = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBugly() {
        /***** Beta高级设置 *****/
        /**
         * true表示app启动自动初始化升级模块; false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = false;

        /**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 1 * 1000;
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 1 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.drawable.app_icon;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.drawable.app_icon;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.drawable.app_icon;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);

        /***** Bugly高级设置 *****/
//        BuglyStrategy strategy = new BuglyStrategy();
        /**
         * 设置app渠道号
         */
//        strategy.setAppChannel(APP_CHANNEL);

//        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        /***** 统一初始化Bugly产品，包含Beta *****/
        Bugly.init(getApplicationContext(), "3bd48bcff6", false);
    }

    private void bindRxBusEvent() {
        refreshDisposable = RxBus.getDefault().register(RefreshEvent.class, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                RefreshEvent refreshEvent = (RefreshEvent) o;
                ZGWToast.show(MainActivity.this, "消息已读");
                badge.setBadgeNumber(0);
            }
        });
    }

    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;
            badge = new QBadgeView(this).bindTarget(view);
            badge.setGravityOffset(spaceWidth + 50, 13, false);
            badge.setBadgeNumber(showNumber);
            // 显示badegeview
//            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
        }
    }
}
