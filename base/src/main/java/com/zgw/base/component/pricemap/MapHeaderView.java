package com.zgw.base.component.pricemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zgw.base.R;
import com.zgw.base.util.PublicViewUtils;

public class MapHeaderView extends View {

    private Context context;

    private Paint rectPaint;//画左上角方块

    private Paint textPaint;//画文字

    private String goUpData;

    public void setData(String goUpData) {
        this.goUpData = goUpData;
    }

    public MapHeaderView(Context context) {
        super(context);
    }

    public MapHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(PublicViewUtils.getPxInt(11, context));
        textPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

//        canvas.drawRect(0,0, PublicViewUtils.getPxInt(25, context),
//                PublicViewUtils.getPxInt(20, context) , rectPaint);

        RectF rectF = new RectF(0, 0, PublicViewUtils.getPxInt(25, context), PublicViewUtils.getPxInt(20, context));// 设置个新的长方形
        canvas.drawRoundRect(rectF, 7, 7, rectPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawText("大涨" + goUpData, PublicViewUtils.getPxInt(30, context),
                PublicViewUtils.getPxInt(15, context), textPaint);

        //上涨
//        canvas.drawRect(PublicViewUtils.getPxInt(80, context),0,
//                PublicViewUtils.getPxInt(105, context),  PublicViewUtils.getPxInt(20, context) , rectPaint);

        RectF rectF1 = new RectF(PublicViewUtils.getPxInt(80, context), 0, PublicViewUtils.getPxInt(105, context),
                PublicViewUtils.getPxInt(20, context));
        canvas.drawRoundRect(rectF1, 7, 7, rectPaint);//第二个参数是x半径，第三个参数是y半径

        canvas.drawText("大涨", PublicViewUtils.getPxInt(110, context),
                PublicViewUtils.getPxInt(15, context), textPaint);

        canvas.drawRect(0,PublicViewUtils.getPxInt(25, context) ,
                PublicViewUtils.getPxInt(25, context),  PublicViewUtils.getPxInt(45, context) , rectPaint);
        canvas.drawText("平稳" , PublicViewUtils.getPxInt(30, context),
                PublicViewUtils.getPxInt(40, context), textPaint);
    }
}
