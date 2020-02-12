package com.zgw.base.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ZGWListView extends ListView {

    public ZGWListView(Context context) {
        super(context);
    }

    public ZGWListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    boolean dspatchTouchEvent;

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    public void setDispatchTouchEvent(boolean dspatchTouchEvent){
        this.dspatchTouchEvent=dspatchTouchEvent;
    }
}
