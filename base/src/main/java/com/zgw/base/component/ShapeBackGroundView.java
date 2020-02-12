package com.zgw.base.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zgw.base.R;


/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class ShapeBackGroundView extends LinearLayout {
    private int solidColor;//填充颜色
    private int stroke_Color;//边颜色
    private int gradientStartColor;//渐变开始的颜色
    private int gradientEndColor;//渐变最后颜色
    private int gradientCenterColor;//渐变中间颜色
    private int touchColor;//按下颜色
    private int cornesRadius;//远的角度
    private int topLeftRadius;//左上角角度
    private int topRightRadius;//右上角角度
    private int bottomLeftRadius;//左下角角度
    private int bottomRightRadius;//右下角角度
    private int stroke_Width;//边线宽度
    private int strokeDashWidth;//
    private int strokeDashGap;
    private int gradientAngle;//渐变角度
    private int gradientRadius;//渐变颜色的半径
    private int gradientType;//渐变
    private int gradientOrientation;
    private int shapeType;
    boolean gradientUseLevel;
    GradientDrawable gradientDrawable;

    public ShapeBackGroundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ShapeBackGroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public ShapeBackGroundView(Context context) {
        super(context);
    }

    private void initData(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs,
                R.styleable.ShapeTextview);
        solidColor = arr.getColor(R.styleable.ShapeTextview_solidColor,
                Color.TRANSPARENT);
        stroke_Color = arr.getColor(R.styleable.ShapeTextview_stroke_Color,
                Color.TRANSPARENT);
        gradientStartColor = arr.getColor(R.styleable.ShapeTextview_gradientStartColor,
                        Color.TRANSPARENT);
        gradientEndColor = arr.getColor(
                R.styleable.ShapeTextview_gradientEndColor, Color.TRANSPARENT);
        gradientCenterColor = arr.getColor(
                R.styleable.ShapeTextview_gradientCenterColor,
                Color.TRANSPARENT);
        touchColor = arr.getColor(R.styleable.ShapeTextview_touchSolidColor,
                Color.TRANSPARENT);

        cornesRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_cornesRadius, 0);
        topLeftRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_topLeftRadius, 0);
        topRightRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_topRightRadius, 0);
        bottomLeftRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_bottomLeftRadius, 0);
        bottomRightRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_bottomRightRadius, 0);
        stroke_Width = (int) arr.getDimension(
                R.styleable.ShapeTextview_stroke_Width, 0);

        strokeDashWidth = (int) arr.getDimension(
                R.styleable.ShapeTextview_strokeDashWidth, 0);
        strokeDashGap = (int) arr.getDimension(
                R.styleable.ShapeTextview_strokeDashGap, 0);
        gradientAngle = (int) arr.getDimension(
                R.styleable.ShapeTextview_gradientAngle, 0);
        gradientRadius = (int) arr.getDimension(
                R.styleable.ShapeTextview_gradientRadius, 0);

        gradientUseLevel = arr.getBoolean(
                R.styleable.ShapeTextview_gradientUseLevel, false);
        gradientType = arr.getInt(R.styleable.ShapeTextview_gradientType, -1);//渐变
        gradientOrientation = arr.getInt(
                R.styleable.ShapeTextview_gradientOrientation, -1);
        shapeType = arr.getInt(
                R.styleable.ShapeTextview_shapeType, -1);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(stroke_Width, stroke_Color, strokeDashWidth,
                strokeDashGap);
        // 如果设定的有Orientation 就默认为是渐变色的Button，否则就是纯色的Button
        if (gradientOrientation != -1) {
            gradientDrawable
                    .setOrientation(getOrientation(gradientOrientation));
            gradientDrawable.setColors(new int[] { gradientStartColor,
                    gradientCenterColor, gradientEndColor });
        } else {
            gradientDrawable.setColor(solidColor);
        }

        if(shapeType != -1){
            gradientDrawable.setShape(shapeType);
        }
        //是否为圆形
        if(shapeType != GradientDrawable.OVAL){
            // 如果设定的有Corner Radius就认为是4个角一样的Button， 否则就是4个不一样的角 Button
            if (cornesRadius != 0) {
                gradientDrawable.setCornerRadius(cornesRadius);
            } else {
                //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                gradientDrawable.setCornerRadii(new float[] { topLeftRadius,
                        topLeftRadius, topRightRadius, topRightRadius,
                        bottomRightRadius, bottomRightRadius, bottomLeftRadius,
                        bottomLeftRadius });
            }
        }

        if (gradientUseLevel) {
            gradientDrawable.setUseLevel(gradientUseLevel);
        }
        //渐变
        if (gradientType != -1){
            gradientDrawable.setGradientType(gradientType);
        }

        gradientDrawable.setGradientRadius(gradientRadius);
//        setBackground(gradientDrawable);
        setBackgroundDrawable(gradientDrawable);

    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if (touchColor != Color.TRANSPARENT) {
//                gradientDrawable.setColor(touchColor);
////                setBackground(gradientDrawable);
//                setBackgroundDrawable(gradientDrawable);
////                invalidate();
//            }
//            break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                if (touchColor != Color.TRANSPARENT) {
//                    gradientDrawable.setColor(solidColor);
////                    setBackground(gradientDrawable);
//                    setBackgroundDrawable(gradientDrawable);
//                }
//            break;
//        }
//        return super.onTouchEvent(event);
//    }

    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
        gradientDrawable.setColor(solidColor);
    }

    private GradientDrawable.Orientation getOrientation(int gradientOrientation) {
        GradientDrawable.Orientation orientation = null;
        switch (gradientOrientation) {
            case 0:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 1:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 2:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 3:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 4:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 5:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            case 6:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 7:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
        }
        return orientation;
    }

}
