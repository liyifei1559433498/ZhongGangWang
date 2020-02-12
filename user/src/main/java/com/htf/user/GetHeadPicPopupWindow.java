package com.htf.user;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class GetHeadPicPopupWindow implements View.OnClickListener {

	private static PopupWindow mGetHeadPicPopupWindow = null;
	private Context context;
	private boolean outsideTouchable = true;

	private TextView take_Photos;
	private TextView select_From_Album;
	private TextView cancel;
	private TextView share_linear_blank;

	private GetHeadPicClickListener getHeadPicClickListener;

	public PopupWindow createGetHeadPicWindow(View parentLayout, Context context) {
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = inflater.inflate(R.layout.headpic_popupwindow, null);

		take_Photos = (TextView) popupView.findViewById(R.id.take_Photos);
		select_From_Album = (TextView) popupView.findViewById(R.id.select_From_Album);
		cancel = (TextView) popupView.findViewById(R.id.cancel);
		share_linear_blank = (TextView) popupView.findViewById(R.id.share_linear_blank);

		mGetHeadPicPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		// 设置SelectPicPopupWindow弹出窗体可点击
		mGetHeadPicPopupWindow.setFocusable(true);
		mGetHeadPicPopupWindow.setOutsideTouchable(outsideTouchable);
		// 刷新状态
		mGetHeadPicPopupWindow.update();
		mGetHeadPicPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置SelectPicPopupWindow弹出窗体动画效果
		mGetHeadPicPopupWindow.setAnimationStyle(R.style.style_share_popupwindow);
		mGetHeadPicPopupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

		take_Photos.setOnClickListener(this);
		select_From_Album.setOnClickListener(this);
		cancel.setOnClickListener(this);
		share_linear_blank.setOnClickListener(this);

		return mGetHeadPicPopupWindow;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (mGetHeadPicPopupWindow.isShowing()) {
			mGetHeadPicPopupWindow.dismiss();
		}
		if (id == R.id.take_Photos) {
			getHeadPicClickListener.takePhotosClick();
		} else if (id == R.id.select_From_Album) {
			getHeadPicClickListener.selectFromAlbumClick();
		}
	}

	public void addGetHeadClickListener(GetHeadPicClickListener getHeadPicClickListener) {
		this.getHeadPicClickListener = getHeadPicClickListener;
	}

	public interface GetHeadPicClickListener {
		void takePhotosClick();

		void selectFromAlbumClick();
	}

}
