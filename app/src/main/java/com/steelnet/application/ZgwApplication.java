package com.steelnet.application;

import com.billy.cc.core.component.CC;
import com.kingja.loadsir.core.LoadSir;
import com.steelnet.activity.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.zgw.base.BaseApplication;
import com.zgw.base.loadsir.CustomCallback;
import com.zgw.base.loadsir.EmptyCallback;
import com.zgw.base.loadsir.ErrorCallback;
import com.zgw.base.loadsir.LoadingCallback;
import com.zgw.base.loadsir.TimeoutCallback;
import com.zgw.base.util.LogUtil;

public class ZgwApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initUmeng();
    }

    private void init() {
        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(false);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }

    private void initUmeng() {
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5de707d20cafb21c68000410", "umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "d56e6d34b76e79628b915c7003d1ab1b");
        PlatformConfig.setWeixin("wxcaa1f509abe69ec2", "db61e0512f6c2c99e94e3d3bbf3e0aa9");
        PlatformConfig.setQQZone("1104803935", "TovGXKDo8Ap0txvR");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtil.outLog("TAG", "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.outLog("TAG", "注册失败：-------->  " + "s == " + s + " == s1 == " + s1);
            }
        });

    }

}
