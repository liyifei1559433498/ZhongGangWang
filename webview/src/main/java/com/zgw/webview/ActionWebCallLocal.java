package com.zgw.webview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.zgw.base.util.JsonUtils;
import com.zgw.base.util.PublicMethod;

public final class ActionWebCallLocal {
    private Handler mHandler;
    private Context mContext;
    private PublicMethod mPublicMethod;
    private WebView mWebView;

    public ActionWebCallLocal(Context context, Handler handler, WebView webView, PublicMethod publicMethod) {
        this.mHandler = handler;
        this.mContext = context;
        this.mWebView = webView;
        this.mPublicMethod = publicMethod;
    }

    @JavascriptInterface
    public void setData(String callBackData) {
        try {
            String titleName = JsonUtils.readjsonString("titleName", callBackData);
            Message msg = mHandler.obtainMessage();
            Bundle data = new Bundle();
            data.putString("titleName", titleName);
            msg.setData(data);
            msg.what = WebViewActivity.GET_H5_INFO;
            msg.sendToTarget();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * h5控制app头部显示隐藏
     */
    @JavascriptInterface
    public void jsSetApp(String result) {
        try {
//            JSONObject jsonObject = new JSONObject(result);
//            LogUtil.outLog("TAG", jsonObject.toString());
//            String type = jsonObject.getString("type");
//            if ("12".equals(type)) {
//                String url = jsonObject.getString("url");
//                startBrower(url);
//            } else if ("13".equals(type)) {
////                String shareType = jsonObject.getString("shareType");
//                ShareBean bean = JsonUtils.resultData(result, ShareBean.class);
//                String shareImgUrl = bean.getShareImgUrl();
//                String shareLinkUrl = ShareUtil.checkUrl(this.mContext, bean.getShareLinkUrl());
//                String shareTitle = bean.getShareTitle();
//                String shareDesc = bean.getShareDesc();
//                Message msg = mHandler.obtainMessage();
//                msg.what = WebViewActivity.LOCAL_CALL_SHARE;
//                Bundle b = new Bundle();
//                b.putString("shareImgUrl", shareImgUrl);
//                b.putString("shareLinkUrl", shareLinkUrl);
//                b.putString("shareTitle", shareTitle);
//                b.putString("shareDesc", shareDesc);
//                msg.setData(b);
//                msg.sendToTarget();

//                SHARE_MEDIA sm = ShareUtil.checkShareType(shareType);
//                boolean isInstall = true;
//                String notHashAppTip = "";
//                if (sm == SHARE_MEDIA.WEIXIN) {
//                    notHashAppTip = "您尚未安装微信!";
//                    isInstall = PublicMethod.isWeixinAvilible(this.mContext);
//                } else if (sm == SHARE_MEDIA.QQ) {
//                    notHashAppTip = "您尚未安装QQ!";
//                    isInstall = PublicMethod.isQQClientAvailable(this.mContext);
//                }
//                if (isInstall) {
//                    ShareUtil.initShare(this.mContext, mPublicMethod, sm,
//                            shareTitle, shareDesc, shareLinkUrl, shareImgUrl, umShareListener);
//                } else {
//                    JZJToast.show(this.mContext, notHashAppTip);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 跳转到微信聊天界面
//     */
//    private void turnToWeiChat() {
//        try {
//            UMShareAPI mShareAPI = UMShareAPI.get(mContext);
//            boolean isWeiXinInstall = mShareAPI.isInstall((Activity) mContext, SHARE_MEDIA.WEIXIN);
//            if (isWeiXinInstall) {
//                Intent intent = new Intent();
//                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");// 报名该有activity
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setComponent(cmp);
//                ((Activity) mContext).startActivity(intent);
//            } else {
//                JZJToast.show(mContext, "您未安装微信客户端！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 复制公众号
//     *
//     * @param str
//     */
//    private void copyPublicNum(String str) {
//        //获取剪贴板管理器：
//        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
//        // 创建普通字符型ClipData
//        ClipData mClipData = ClipData.newPlainText(str, str);
//        // 将ClipData内容放到系统剪贴板里。
//        cm.setPrimaryClip(mClipData);
//    }
//
//    private void startBrower(String url) {
//        try {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            ((Activity) mContext).startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            mWebView.loadUrl("javascript:clientShareSucc('" + platform.toString() + "')");
//            ShareUtil.showMessage(mContext, platform, " 分享成功啦");
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            ShareUtil.showMessage(mContext, platform, " 分享失败啦");
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            ShareUtil.showMessage(mContext, platform, " 分享取消了");
//        }
//
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//    };
}
