package com.zgw.webview;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.component.SharePopupWindow;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.JsonUtils;
import com.zgw.base.util.LogUtil;
import com.zgw.base.util.NetUtil;
import com.zgw.base.util.PublicMethod;
import com.zgw.base.util.ZGWToast;
import com.zgw.webview.bean.ShareBean;

import java.net.URLDecoder;

import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private String linkUrl;

    private WebView webView;

    private RelativeLayout topLayout;

    private FrameLayout topBackBtn;

    private TextView topTitle;

    private TextView rightTitle;

    private NetChangeReceiver netChangeReceiver;

    private boolean is3gConnected = true;//是否是3g网络连接

    private boolean isWiFiConnected = true;//是否是WiFi网络连接

    private boolean isNoNetConnected = true;//是否无网络连接

    private ProgressBar progressBar;

    private Window window;

    private ValueCallback<Uri> mUploadCallbackBelow;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    private static final int GET_INFO_SUCCESS = 0;

    private static final int GET_INFO_FAIL = 1;

    public static final int GET_H5_INFO = 3;

    public static final int LOCAL_CALL_SHARE = 9;//初始化js回调的分享参数

    private PublicMethod publicMethod = new PublicMethod();

    //分享时需要的
    private String shareImgUrl;

    private String shareLinkUrl;

    private String shareTitle;

    private String shareDesc;

    protected static final int REQUEST_CODE = 2;

    private Uri imageUri;

    private SharePopupWindow sharePopupWindow;

    LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarView(R.id.top_view).statusBarColor(R.color.orange).init();
        initView();
    }

    private void initView() {
        webView = $(R.id.webView);
        topLayout = $(R.id.topLayout);
        topBackBtn = $(R.id.topBackBtn);
        topTitle = $(R.id.topTitle);
        rightTitle = $(R.id.rightTitle);
        progressBar = $(R.id.progressBar);
        parentLayout = $(R.id.container);
        topBackBtn.setOnClickListener(this);
//        linkUrl = getIntent().getStringExtra("linkUrl");
        linkUrl = "https://wlbservicet.zgw.com/guide/index.html";
        if (linkUrl.contains("title=true")) {
            topLayout.setVisibility(View.VISIBLE);
            topBackBtn.setOnClickListener(this);
        }
        //网络状态变化广播监听
        regReceiver();
        window = getWindow();
        /**初始化webview的参数*/
        progressBar.setMax(100);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setDomStorageEnabled(true);
        /**根据获取的url设置ui状态*/
        checkUrlToViewState();
    }

    /**
     * 判断url来初始化不同界面
     */
    private void checkUrlToViewState() {
        if (!TextUtils.isEmpty(linkUrl)) {
            webView.setVisibility(View.VISIBLE);
            /**webview请求url地址*/
            initWebToLoadUrl();
        }
    }

    /**
     * 给webview添加监听
     */
    private void initWebToLoadUrl() {
        if (PublicMethod.checkWirelessNetwork(this)) {
            webView.loadUrl(linkUrl);
        } else {
            if (linkUrl.contains("untitled=true")) {
                webView.loadUrl("file:///android_asset/titlewifi.html");
            } else {
                webView.loadUrl("file:///android_asset/wifi.html");
            }
        }
        webView.addJavascriptInterface(new ActionWebCallLocal(this, handler, webView, publicMethod), "webCallLocal");

        if (Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17) {
            fixWebView();
        }

    }

    /**
     * 删除默认添加的"searchBoxJavaBridge_"
     */
    @TargetApi(11)
    private void fixWebView() {
        //        http://50.56.33.56/blog/?p=314
        //        http://drops.wooyun.org/papers/548
        // We hadn't use addJavascriptInterface, but WebView add "searchBoxJavaBridge_" to mJavaScriptObjects below API 17 by default:
        // mJavaScriptObjects.put(SearchBoxImpl.JS_INTERFACE_NAME, mSearchBox);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case GET_INFO_SUCCESS:
//                        ActivityQueryBean activityQueryBean = (ActivityQueryBean) msg.obj;
//                        initDataFromNet(activityQueryBean);
                        break;
                    case GET_INFO_FAIL:
                        Bundle data = msg.getData();
                        String message = data.getString("message");
                        ZGWToast.show(WebViewActivity.this, message);
                        break;
                    case GET_H5_INFO:
                        Bundle topInfo = msg.getData();
                        String titleName = topInfo.getString("titleName");
                        topTitle.setText(titleName);
                        break;
                    case LOCAL_CALL_SHARE:
                        Bundle shareInfo = msg.getData();
                        shareImgUrl = shareInfo.getString("shareImgUrl");
                        shareLinkUrl = shareInfo.getString("shareLinkUrl");
                        shareTitle = shareInfo.getString("shareTitle");
                        shareDesc = shareInfo.getString("shareDesc");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.outLog("TAG", url);
//            resetView(url);
            if (url.contains("shareTrue")) {
                //分享
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                if (url.contains("nonet") || url.contains("android_asset/wifi.html")
                        || url.contains("android_asset/titlewifi.html")) {
                    String oveeideUrl = changeUrl();
//                                webviewPlayDescription.loadUrl("javascript:nonet('" + oveeideUrl + "')");
                    if (linkUrl.contains("?")) {
                        webView.loadUrl("javascript:nonet('" + oveeideUrl + "&nonet=false" + "')");
                    } else {
                        webView.loadUrl("javascript:nonet('" + oveeideUrl + "?nonet=false" + "')");
                    }
                }
                webView.clearHistory();

                /**重置网络状态*/
                resetNetState();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 重置网络状态
         */
        private void resetNetState() {
            is3gConnected = true;
            isWiFiConnected = true;
            isNoNetConnected = true;
            NetChange();
        }

        private String changeUrl() {
            String midUrl = "";
            if (linkUrl.contains("&newWebview=true")) {
                midUrl = linkUrl.toString().trim().replace("&newWebview=true", "");
            } else if (linkUrl.contains("?newWebview=true")) {
                if (linkUrl.contains("newWebview=true&")) {
                    midUrl = linkUrl.toString().trim().replace("newWebview=true&", "");
                } else {
                    midUrl = linkUrl.toString().trim().replace("?newWebview=true", "");
                }
            } else {
                midUrl = linkUrl;
            }
            return midUrl;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }


        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
//            view.loadUrl("javascript:document.body.innerHTML=\"" + "" + "\" ");
//            if (failingUrl.contains("untitled=true")) {
//                webViewPlayDescription.loadUrl("file:///android_asset/titlewifi.html");
//            } else {
//                webViewPlayDescription.loadUrl("file:///android_asset/wifi.html");
//            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }

        }

        /**
         * 8(Android 2.2) <= API <= 10(Android 2.3)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            LogUtil.outLog("TAG", "运行方法 openFileChooser-1");
            // (2)该方法回调时说明版本API < 21，此时将结果赋值给 mUploadCallbackBelow，使之 != null
            mUploadCallbackBelow = uploadMsg;
            takePhoto();
        }

        /**
         * 11(Android 3.0) <= API <= 15(Android 4.0.3)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            LogUtil.outLog("TAG", "运行方法 openFileChooser-2 (acceptType: " + acceptType + ")");
            openFileChooser(uploadMsg);
        }

        /**
         * 16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            LogUtil.outLog("TAG", "运行方法 openFileChooser-3 (acceptType: " + acceptType + "; capture: " + capture + ")");
            openFileChooser(uploadMsg);
        }

        /**
         * API >= 21(Android 5.0.1)回调此方法
         */
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            LogUtil.outLog("TAG", "运行方法 onShowFileChooser");
            // (1)该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
            mUploadCallbackAboveL = filePathCallback;
            takePhoto();
            return true;
        }

    };

    private void takePhoto() {
        // 指定拍照存储位置的方式调起相机
//        String filePath = Environment.getExternalStorageDirectory() + File.separator
//                + Environment.DIRECTORY_PICTURES + File.separator;
//        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//        imageUri = Uri.fromFile(new File(filePath + fileName));
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_CODE);


        // 选择图片（不包括相机拍照）,则不用成功后发刷新图库的广播
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE);
    }

    private void regReceiver() {
        netChangeReceiver = new NetChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netChangeReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.topBackBtn) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void chooseBelow(int resultCode, Intent data) {
        LogUtil.outLog("TAG", "返回调用方法--chooseBelow");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    LogUtil.outLog("TAG", "系统返回URI：" + uri.toString());
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                LogUtil.outLog("TAG", "自定义结果：" + imageUri.toString());
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            updatePhotos();
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        LogUtil.outLog("TAG", "返回调用方法--chooseAbove");

        if (RESULT_OK == resultCode) {
            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    for (Uri uri : results) {
                        LogUtil.outLog("TAG", "系统返回URI：" + uri.toString());
                    }
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                LogUtil.outLog("TAG", "自定义结果：" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            updatePhotos();
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }


    /***
     * 网络监听器
     */
    public class NetChangeReceiver extends BroadcastReceiver {
        public NetChangeReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            try {
                NetChange();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断网络状态变化
     */
    private void NetChange() {
        if (NetUtil.isMOBILEConnected(this)) {
            if (is3gConnected) {
                is3gConnected = false;
                isWiFiConnected = true;
                isNoNetConnected = true;
                webView.loadUrl("javascript:appSetJs('" + publicMethod.splitH5Parameter("3", "3g") + "')");
//                PublicMethod.outLog("111111111111111111111", "3g3g3g3g3g3g3g3g3g3g3g3g");
            }
        } else if (NetUtil.isWIFIConnected(this)) {
            if (isWiFiConnected) {
                is3gConnected = true;
                isWiFiConnected = false;
                isNoNetConnected = true;
                webView.loadUrl("javascript:appSetJs('" + publicMethod.splitH5Parameter("3", "wifi") + "')");
            }
        } else {
            if (isNoNetConnected) {
                is3gConnected = true;
                isWiFiConnected = true;
                isNoNetConnected = false;
                webView.loadUrl("javascript:appSetJs('" + publicMethod.splitH5Parameter("3", "nonet") + "')");
            }
        }
    }

    private void toShowSharePoP(String url) {
        if (sharePopupWindow == null) {
            sharePopupWindow = new SharePopupWindow();
        }
        try {
            int index = url.indexOf("=");
            String jsonUrl = url.substring(index + 1, url.length());
            String decodeJsonUrl = URLDecoder.decode(jsonUrl, "UTF-8");
            ShareBean bean = JsonUtils.resultData(decodeJsonUrl, ShareBean.class);
            shareImgUrl = bean.getShareImgUrl();
            shareLinkUrl = bean.getShareLinkUrl();
            shareTitle = bean.getShareTitle();
            shareDesc = bean.getShareDesc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharePopupWindow.createWindow(parentLayout, this, shareImgUrl, shareLinkUrl, shareTitle, shareDesc);
        sharePopupWindow.setPublicMethod(publicMethod);

//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netChangeReceiver);
        webView.clearHistory();
        webView.destroy();
    }
}
