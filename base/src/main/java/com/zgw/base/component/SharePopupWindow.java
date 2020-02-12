package com.zgw.base.component;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zgw.base.R;
import com.zgw.base.util.PublicMethod;
import com.zgw.base.util.ShareUtil;
import com.zgw.base.util.ZGWToast;

/**
 * Created by Shenshiqiang
 * 2016/3/30 11:13.
 *
 * @Description: 自定义友盟分享面板以及与之相关的操作
 */
public class SharePopupWindow implements View.OnClickListener {

    private static PopupWindow mSharePopupWindow = null;
    private boolean outsideTouchable = true;
    private View home_view = null;

    //分享时需要的
    private String shareImgUrl;
    private String shareLinkUrl;
    private String shareTitle;
    private String shareDesc;

//    private UMImage image = null;

    private LinearLayout share_weichat;
    private LinearLayout share_weichat_cicle;
    private LinearLayout share_qq;
    private LinearLayout share_qqzone;
    private TextView share_button_cancel;
    private TextView share_linear_blank;

    private PublicMethod publicMethod;

    private Context context;

    private String mPlatform;

    public PopupWindow createWindow(View parentLayout, Context context, String shareImgUrl, String shareLinkUrl, String shareTitle, String shareDesc) {
        this.home_view = parentLayout;
        this.context = context;
        this.shareImgUrl = shareImgUrl;
        this.shareLinkUrl = shareLinkUrl;
        this.shareTitle = shareTitle;
        this.shareDesc = shareDesc;
        this.shareLinkUrl = ShareUtil.checkUrl(context, this.shareLinkUrl);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = (LinearLayout) inflater.inflate(R.layout.share_popupwindow, null);

        share_weichat = (LinearLayout) popupView.findViewById(R.id.share_weichat);
        share_weichat_cicle = (LinearLayout) popupView.findViewById(R.id.share_weichat_cicle);
        share_qq = (LinearLayout) popupView.findViewById(R.id.share_qq);
        share_qqzone = (LinearLayout) popupView.findViewById(R.id.share_qqzone);
        share_button_cancel = (TextView) popupView.findViewById(R.id.share_button_cancel);
        share_linear_blank = (TextView) popupView.findViewById(R.id.share_linear_blank);
//        hideWeiChat();
        mSharePopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        mSharePopupWindow.setFocusable(true);
        mSharePopupWindow.setOutsideTouchable(outsideTouchable);
        // 刷新状态
        mSharePopupWindow.update();
        mSharePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体动画效果
        mSharePopupWindow.setAnimationStyle(R.style.style_share_popupwindow);
        mSharePopupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        share_weichat.setOnClickListener(this);
        share_weichat_cicle.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qqzone.setOnClickListener(this);
        share_button_cancel.setOnClickListener(this);
        share_linear_blank.setOnClickListener(this);

        return mSharePopupWindow;
    }

    public void setPublicMethod(PublicMethod publicMethod) {
        this.publicMethod = publicMethod;
    }

    private void hideWeiChat() {
        try {
            String packageName = context.getPackageName();
            if (!"com.jiezhijie.jieyoulian".equals(packageName)) {
                share_weichat.setVisibility(View.GONE);
                share_weichat_cicle.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        SHARE_MEDIA shareMedia = SHARE_MEDIA.GOOGLEPLUS;
        String notHashAppTip = "";
        boolean isInstall = true;
        int id = v.getId();
        if (id == R.id.share_weichat) {
            shareMedia = SHARE_MEDIA.WEIXIN;
            notHashAppTip = "您尚未安装微信!";
            isInstall = PublicMethod.isWeixinAvilible(this.context);
        } else if (id == R.id.share_weichat_cicle) {
            shareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
            notHashAppTip = "您尚未安装微信!";
            isInstall = PublicMethod.isWeixinAvilible(this.context);
        } else if (id == R.id.share_qq) {
            shareMedia = SHARE_MEDIA.QQ;
            notHashAppTip = "您尚未安装QQ!";
            isInstall = PublicMethod.isQQClientAvailable(this.context);
        } else if (id == R.id.share_qqzone) {
            shareMedia = SHARE_MEDIA.QZONE;
            notHashAppTip = "您尚未安装QQ!";
            isInstall = PublicMethod.isQQClientAvailable(this.context);
        } else if (id == R.id.share_button_cancel || id == R.id.share_linear_blank) {
            if (mSharePopupWindow.isShowing()) {
                mSharePopupWindow.dismiss();
            }
        }
        if (isInstall){
            if (shareMedia == SHARE_MEDIA.WEIXIN || shareMedia == SHARE_MEDIA.WEIXIN_CIRCLE
                    || shareMedia == SHARE_MEDIA.QQ || shareMedia == SHARE_MEDIA.QZONE) {
                ShareUtil.initShare(context, publicMethod, shareMedia, shareTitle, shareDesc, shareLinkUrl,shareImgUrl, umShareListener);
            }
        }else {
            ZGWToast.show(this.context, notHashAppTip);
        }

    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            mSharePopupWindow.dismiss();
           /* if (context instanceof CommonWebViewActivity) {
                ((CommonWebViewActivity) context).shareSuccess(platform.toString());
            } else */
//            if (context instanceof ActionDetailActivity) {
//                ((ActionDetailActivity) context).shareSuccess(platform.toString());
//            }
            ShareUtil.showMessage(context, platform, " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            mSharePopupWindow.dismiss();
            ShareUtil.showMessage(context, platform, " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            mSharePopupWindow.dismiss();
            ShareUtil.showMessage(context, platform, " 分享取消了");
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }
    };

}
