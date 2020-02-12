package com.zgw.webview;

import com.zgw.base.util.PublicMethod;

public class ShareUtil {

    private static final String SHARE_TYPE_QQ = "tencent";
    private static final String SHARE_TYPE_QQ_SPACE = "qqSpace";
    private static final String SHARE_TYPE_WECHAT = "weChat";
    private static final String SHARE_TYPE_WECHAT_FRIENDCIRCLE = "friendCircle";

    private PublicMethod publicMethod;

//    /**
//     * 初始化分享信息
//     *
//     * @param shareMedia
//     */
//    public static void initShare(Context context, PublicMethod publicMethod, SHARE_MEDIA shareMedia, String shareTitle,
//                                 String shareDesc, String shareLinkUrl, String shareImgUrl,
//                                 UMShareListener umShareListener) {
//        try {
//            ShareAction shareAction = new ShareAction((Activity) context);
//            UMImage image = null;
//            if (!TextUtils.isEmpty(shareImgUrl)) {
//                image = new UMImage(context, shareImgUrl);
//            }
//            shareAction.setPlatform(shareMedia)
//                    .setCallback(umShareListener);
////            }
//            UMWeb umWeb = new UMWeb(shareLinkUrl);
//            umWeb.setTitle(shareTitle);//标题
//            umWeb.setThumb(image);  //缩略图
//            umWeb.setDescription(shareDesc);//描述
//            shareAction = shareAction.withMedia(umWeb);
//            shareAction.share();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void showMessage(Context context, SHARE_MEDIA platform, String message) {
//        String mPlatform = "";
//        if (platform.toString().equals("WEIXIN")) {
//            mPlatform = "微信好友";
//        } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
//            mPlatform = "微信朋友圈";
//        } else if (platform.toString().equals("QQ")) {
//            mPlatform = "QQ好友";
//        } else if (platform.toString().equals("QZONE")) {
//            mPlatform = "QQ空间";
//        }
//        JZJToast.show(context, mPlatform + message);
//    }
//
//    public static String checkUrl(Context context, String url) {
//        try {
//
//            if (!url.contains("?")) {
//                url = url + "?";
//            }
//
//            if (!url.contains("version")) {
//                if (url.endsWith("?")) {
//                    url = url + "version=" + Constants.SOFTWARE_VERSION;
//                } else {
//                    url = url + "&version=" + Constants.SOFTWARE_VERSION;
//                }
//            }
//
//            if (!url.contains("productName")) {
//                if (TextUtils.isEmpty(Constants.PRODUCT_NAME)) {
//                    Constants.PRODUCT_NAME = PublicMethod.getProductName(context);
//                }
//
//                url = url + "&productName=" + Constants.PRODUCT_NAME;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//    public static SHARE_MEDIA checkShareType(String shareType) {
//        SHARE_MEDIA shareMedia = SHARE_MEDIA.QQ;
//        switch (shareType) {
//            case SHARE_TYPE_QQ:
//                shareMedia = SHARE_MEDIA.QQ;
//                break;
//            case SHARE_TYPE_QQ_SPACE:
//                shareMedia = SHARE_MEDIA.QZONE;
//                break;
//            case SHARE_TYPE_WECHAT:
//                shareMedia = SHARE_MEDIA.WEIXIN;
//                break;
//            case SHARE_TYPE_WECHAT_FRIENDCIRCLE:
//                shareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
//                break;
//        }
//
//        return shareMedia;
//    }
}
