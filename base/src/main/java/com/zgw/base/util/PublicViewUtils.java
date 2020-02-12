package com.zgw.base.util;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import java.util.List;
import java.util.Random;

public class PublicViewUtils {

	/**
	 * 屏幕的密度
	 * @return
	 */
	public static float getDensity(Activity activity) {
		if (activity != null) {
			DisplayMetrics metric = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
			return metric.density;
		}
		return 1;
	}
	
	/**
	 * 获取屏幕的高
	 * @param cx
	 * @return
	 */
	public static int getDisplayHeight(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}
	
	/**
	 * 获取屏幕的宽
	 * @param cx
	 * @return
	 */
	public static int getDisplayWidth(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}
	
	public static int getPxInt(float dip, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dip, context.getResources().getDisplayMetrics());
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 数组排序
	 * 
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]) {
		int t_s[] = t;
		int temp;
		for (int i = 0; i < t_s.length; i++) {
			for (int j = i + 1; j < t_s.length; j++) {
				if (t_s[i] > t_s[j]) {
					temp = t_s[i];
					t_s[i] = t_s[j];
					t_s[j] = temp;
				}
			}
		}
		return t_s;
	}
	
	/**
	 * 设置同一个TextView中显示不同颜色的文字
	 * 
	 * @param text  需要显示文字的TextView
	 * @param content 内容
	 * @param start 第一个颜色开始位置
	 * @param end 结束位置
	 * @param color 文字颜色
	 */
	public static void setTextColor(TextView text, String content, int start, int end, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(content);
		builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_COMPOSING);
		text.setText(builder, BufferType.EDITABLE);
	}
	
}
