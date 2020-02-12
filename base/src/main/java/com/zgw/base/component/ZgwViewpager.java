package com.zgw.base.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * 资讯viewpager
 */

public class ZgwViewpager extends ViewPager {

    private boolean isCanScrollable = true;
    /**是否支持点击tab滑动切换，之前如果设置了isCanScrollable为false，tab点击事也无法切换，避免影响之前共用的逻辑*/
    private boolean isCanClickScrollable = false;

    ViewPagerScroller scroller;

    public ZgwViewpager(Context context) {
        this(context, null);
    }

    public ZgwViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (Math.abs(getCurrentItem() - item) > 1) {
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }

    private void init() {
        scroller = new ViewPagerScroller(getContext());
        Class<ViewPager> cl = ViewPager.class;
        try {
            Field field = cl.getDeclaredField("mScroller");
            field.setAccessible(true);
            //利用反射设置mScroller域为自己定义的MScroller
            field.set(this, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScrollable) {
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScrollable) {
            try {
                return super.onTouchEvent(arg0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return false;
        }

    }

    @Override
    public void scrollTo(int x, int y){
        if (isCanScrollable || isCanClickScrollable){
            super.scrollTo(x, y);
        }
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewPager && v != this ) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    /**
     * canClickScrollable这个条件是建立在isCanScrollable不能滑动前提下的
     * @param value
     * @param canClickScrollable
     */
    public void setCanScrollable(boolean value, boolean canClickScrollable) {
        this.isCanScrollable = value;
        if (!this.isCanScrollable){
            this.isCanClickScrollable = canClickScrollable;
        }
    }

    public boolean isCanScrollable() {
        return this.isCanScrollable;
    }

}
