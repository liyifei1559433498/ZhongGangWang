package com.htf.user.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.htf.user.GetHeadPicPopupWindow;
import com.htf.user.R;
import com.htf.user.ui.FeedbackActivity;
import com.htf.user.ui.SettingActivity;
import com.zgw.base.component.CutCircleHeadImg;
import com.zgw.base.component.SelectorButton;
import com.zgw.base.picselector.PictureSelector;
import com.zgw.base.picselector.config.PictureConfig;
import com.zgw.base.picselector.config.PictureMimeType;
import com.zgw.base.picselector.entity.LocalMedia;
import com.zgw.base.picselector.tools.PictureFileUtils;
import com.zgw.base.ui.BaseFragment;
import com.zgw.base.util.LogUtil;
import com.zgw.base.util.ZGWToast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends BaseFragment implements View.OnClickListener {

    RelativeLayout messageLayout;

    RelativeLayout safetyLayout;

    RelativeLayout settingLayout;

    RelativeLayout contentLayout;

    RelativeLayout kefuLayout;

    LinearLayout orderLayout;

    LinearLayout findLayout;

    LinearLayout attentionLayout;

    LinearLayout quoteRecordLayout;

    SelectorButton contentBtn;

    RelativeLayout backLayout;

    ImageView loginBtn;

    TextView userNameBtn;

    CutCircleHeadImg headPic;

    private List<LocalMedia> selectList = new ArrayList<>();

    private GetHeadPicPopupWindow getHeadPicPopupWindow;

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_layout, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        getHeadPicPopupWindow = new GetHeadPicPopupWindow();
        messageLayout = $(view, R.id.messageLayout);
        safetyLayout = $(view, R.id.safetyLayout);
        settingLayout = $(view, R.id.settingLayout);
        contentLayout = $(view, R.id.contentLayout);
        kefuLayout = $(view, R.id.kefuLayout);
        orderLayout = $(view, R.id.orderLayout);
        findLayout = $(view, R.id.findLayout);
        attentionLayout = $(view, R.id.attentionLayout);
        quoteRecordLayout = $(view, R.id.quoteRecordLayout);
        contentBtn = $(view, R.id.contentBtn);
        backLayout = $(view, R.id.backLayout);
        loginBtn = $(view, R.id.loginBtn);
        userNameBtn = $(view, R.id.userNameBtn);
        headPic = $(view, R.id.headPic);
        headPic.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        safetyLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        contentLayout.setOnClickListener(this);
        kefuLayout.setOnClickListener(this);
        orderLayout.setOnClickListener(this);
        findLayout.setOnClickListener(this);
        attentionLayout.setOnClickListener(this);
        quoteRecordLayout.setOnClickListener(this);
        backLayout.setOnClickListener(this);
        userNameBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);


        getHeadPicPopupWindow.addGetHeadClickListener(new GetHeadPicPopupWindow.GetHeadPicClickListener() {
            @Override
            public void takePhotosClick() {
                headPic(true);
            }

            @Override
            public void selectFromAlbumClick() {
                headPic(false);
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id ==  R.id.messageLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.safetyLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.settingLayout) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.contentLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.backLayout) {
            Intent backIntent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(backIntent);
        } else if (id == R.id.kefuLayout) {
            CCResult result = CC.obtainBuilder("webView")
                    .setActionName("WebViewActivity")
                    .build()
                    .call();
        } else if (id == R.id.orderLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.findLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.attentionLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.quoteRecordLayout) {
            ZGWToast.show(getActivity(), "Are you OK ?");
        } else if (id == R.id.loginBtn) {
            login();
        } else if (id == R.id.userNameBtn) {
            login();
        } else if (id == R.id.headPic) {
            getHeadPicPopupWindow.createGetHeadPicWindow(headPic, getActivity());
        }
    }

    private void headPic(boolean flag) {
        selectList.clear();
        if (flag) {
            // 单独拍照
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
//                .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                .previewImage(cb_preview_img.isChecked())// 是否可预览图片
                    .enableCrop(true)// 是否裁剪
                    .compress(false)// 是否压缩
//                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                    .rotateEnabled(false) // 裁剪是否可旋转图片
                    //.scaleEnabled()// 裁剪是否可放大缩小图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        } else {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                    .previewImage(false)// 是否可预览图片
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(true)// 是否裁剪
                    .compress(false)// 是否压缩
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(true)// 是否圆形裁剪
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    .rotateEnabled(false) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    }

    private void login() {
        CC.obtainBuilder("userComponent")
                .setActionName("LoginActivity")
                .addParam("test", "纳斯hi试试")
                .setContext(getContext())
                .build()
                .callAsyncCallbackOnMainThread(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {
                        if (result.isSuccess()) {
                            String data = (String) result.getDataMap().get("biubiu");
                            LogUtil.outLog("TAG", "data == " + data);
                        } else {
                            String errorMessage = result.getErrorMessage();
                            LogUtil.outLog("TAG", "errorMessage == " + errorMessage);
                            ZGWToast.show(getContext(), "登录取消");
                        }

                    }
                });
    }

//    @Inject
//    private GetHeadPicPopupWindow getHeadPicPopupWindow;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.outLog("TAG", "sss");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    try {
                        selectList = PictureSelector.obtainMultipleResult(data);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        Bitmap bitmap = BitmapFactory.decodeFile(selectList.get(0).getCutPath(), getBitmapOption(1)); //将图片的长和宽缩小味原来的1/2
                        headPic.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PictureFileUtils.deleteExternalCacheDirFile(getContext());
    }
}
