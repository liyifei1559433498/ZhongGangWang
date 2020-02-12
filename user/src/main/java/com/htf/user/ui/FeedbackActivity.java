package com.htf.user.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.htf.user.R;
import com.htf.user.adapter.FeedbackGridImageAdapter;
import com.htf.user.compontent.FeedbackPopupWindow;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.component.FullyGridLayoutManager;
import com.zgw.base.picselector.PictureSelector;
import com.zgw.base.picselector.config.PictureConfig;
import com.zgw.base.picselector.config.PictureMimeType;
import com.zgw.base.picselector.entity.LocalMedia;
import com.zgw.base.picselector.permissions.RxPermissions;
import com.zgw.base.picselector.tools.PictureFileUtils;
import com.zgw.base.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout topLayout;

    FrameLayout topBackBtn;

    TextView topTitle;

    RelativeLayout typeLayout;

    LinearLayout parentLayout;

    private List<String> dataList = new ArrayList<>();

    private FeedbackPopupWindow popupWindow;

    RecyclerView recyclerView;

    private FeedbackGridImageAdapter adapter;

    private List<LocalMedia> selectList = new ArrayList<>();

    EditText messageEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_layout);
        ImmersionBar.with(this).statusBarView(R.id.top_view).statusBarColor(R.color.orange).init();
        initView();
    }

    private void initView() {
        topLayout = $(R.id.topLayout);
        topBackBtn = $(R.id.topBackBtn);
        topTitle = $(R.id.topTitle);
        typeLayout = $(R.id.typeLayout);
        parentLayout = $(R.id.parentLayout);
        recyclerView = $(R.id.recyclerView);
        messageEdit = $(R.id.messageEdit);
        topLayout.setBackgroundResource(R.color.white);
        topBackBtn.setOnClickListener(this);
        typeLayout.setOnClickListener(this);
        topTitle.setText("意见反馈");
        topTitle.setTextColor(getResources().getColor(R.color.stock_top_default_color));
        dataList.add("商品相关");
        dataList.add("行情数据");
        dataList.add("客户服务");
        dataList.add("资讯内容");
        dataList.add("产品体验");
        dataList.add("产品功能");
        dataList.add("其他问题");

        messageEdit.clearFocus();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new FeedbackGridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(6);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new FeedbackGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(FeedbackActivity.this).externalPicturePreview(position, selectList);
                    }
                }
            }
        });

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(FeedbackActivity.this);
                } else {
                    Toast.makeText(FeedbackActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private FeedbackGridImageAdapter.onAddPicClickListener onAddPicClickListener = new FeedbackGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(FeedbackActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(6)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        }

    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBackBtn) {
            finish();
        } else if (id == R.id.typeLayout) {
            if (popupWindow == null) {
                popupWindow = new FeedbackPopupWindow(this, dataList, parentLayout);
            }
            popupWindow.showPopupWindow(parentLayout);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.outLog("TAG", "onActivityResult === ");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        showResult(media);
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void showResult(LocalMedia localMedia) {
        int[] originSize = computeSize(new File(localMedia.getPath()));
        int[] thumbSize = computeSize(new File(localMedia.getCompressPath()));
        String originArg = String.format(Locale.CHINA, "原图参数：%d*%d, %dk", originSize[0], originSize[1], new File(localMedia.getPath()).length() >> 10);
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], new File(localMedia.getCompressPath()).length() >> 10);
        LogUtil.outLog("TAG", " originArg == " + originArg);
        LogUtil.outLog("TAG", " thumbArg == " + thumbArg);
    }

    private int[] computeSize(File srcImg) {
        int[] size = new int[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;

        return size;
    }
}
