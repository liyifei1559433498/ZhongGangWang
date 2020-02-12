package com.htf.user.compontent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htf.user.R;
import com.htf.user.R2;
import com.htf.user.adapter.FeedBackTypeAdapter;
import com.zgw.base.component.ShapeTextView;

import java.util.List;


public class FeedbackPopupWindow extends PopupWindow {

    private View contentView;


    private TextView title;

    private Activity context;

    private View parentLayout;

    private GridView gridView;

    private ShapeTextView selectedBtn;

    private ImageView feedCancleBtn;

    @SuppressLint("WrongConstant")
    public FeedbackPopupWindow(final Activity context, List<String> dataList, View parentLayout) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        contentView = inflater.inflate(R.layout.feedback_popupwindow, null); // 设置SelectPicPopupWindow的View
        gridView = contentView.findViewById(R.id.gridView);
        selectedBtn = contentView.findViewById(R.id.selectedBtn);
        feedCancleBtn = contentView.findViewById(R.id.feedCancleBtn);
        this.setContentView(contentView); // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(parentLayout.getWidth());
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.parentLayout = parentLayout;
        // 刷新状态
        this.update();
        this.setOutsideTouchable(false);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        //解决软键盘挡住弹窗问题
        this.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.style_share_popupwindow);

        FeedBackTypeAdapter typeAdapter = new FeedBackTypeAdapter(context);
        typeAdapter.setDataList(dataList);
        gridView.setAdapter(typeAdapter);

        ButtonClickListener buttonClickListener = new ButtonClickListener();
        feedCancleBtn.setOnClickListener(buttonClickListener);
        selectedBtn.setOnClickListener(buttonClickListener);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });


    }

    public class ButtonClickListener implements View.OnClickListener {

        @SuppressLint("InvalidR2Usage")
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R2.id.feedCancleBtn:
                    dismissPopupWindow();
                    break;

                case R2.id.selectedBtn:
                    dismissPopupWindow();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 显示popupWindow * * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            this.showAsDropDown(parent);
            this.showAtLocation(parentLayout, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            darkenBackground(0.9f);
            //弹出时让页面背景回复给原来的颜色降低透明度，让背景看起来变成灰色
        }
    }

    /**
     * 关闭popupWindow
     */
    public void dismissPopupWindow() {
        this.dismiss();
        darkenBackground(1f);
    }

    /**
     * 改变背景颜色，主要是在PopupWindow弹出时背景变化，通过透明度设置
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgcolor;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


}