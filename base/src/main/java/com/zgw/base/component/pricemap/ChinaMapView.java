package com.zgw.base.component.pricemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zgw.base.util.PublicViewUtils;

import java.util.ArrayList;
import java.util.List;


public class ChinaMapView extends View {

    private float viewWidth;//View的宽度

    private Paint innerPaint;//画省份的内部画笔

    private Paint outerPaint;//画省份外圈画笔

    private Paint textPaint;//字体画笔

    private Paint headerTextPaint;//字体画笔

    private TextPaint bottomTextPaint;//字体画笔


    private boolean isFirst; //是否是第一次绘制,用于最初的适配

    private ScrollScaleGestureDetector scrollScaleGestureDetector;//自定义的缩放拖拽手势帮助类

    private ChinaMapModel map;

    private float map_scale = 0;

    private int selectPosition;

    private Context context;

    private Paint rectPaint;//画左上角方块

    private ScrollScaleGestureDetector.OnScrollScaleGestureListener onScrollScaleGestureListener = new ScrollScaleGestureDetector.OnScrollScaleGestureListener() {
        @Override
        public void onClick(float x, float y) {
            //只有点击在某一个省份内才会触发省份选择接口
            for (ProvinceModel p : map.getProvinceslist()) {
                for (Region region : p.getRegionList()) {
                    if (region.contains((int) x, (int) y)) {

                        //重置上一次选中省份的状态
                        map.getProvinceslist().get(selectPosition).setSelect(false);
                        map.getProvinceslist().get(selectPosition).setLinecolor(Color.GRAY);
                        //设置新的选中的省份
                        p.setSelect(true);
                        p.setLinecolor(Color.BLACK);
                        //暴露到Activity中的接口，把省的名字传过去
                        onProvinceClickLisener.onChose(p.getName());
                        invalidate();
                        return;
                    }
                }
            }

        }
    };

    private onProvinceClickLisener onProvinceClickLisener;

    private int displayHeight;

    private String bottomText = "标准说明：中钢网行情地图，即中钢网会员交易指导价全国版，" +
            "包含每日全国主要城市标准商品交易区域行情价格走势等数据信息，为终端用户、金融投资等相关各方提供价格指导参考。" +
            "五类色标分别代表着不同涨跌幅度，即紫色为单日价格上涨50元/吨以上；红色为单日价格上涨50元/吨以内；蓝色为单日价格平稳；" +
            "绿色为单日价格下跌50元/吨以内；灰色为单日价格下跌50元/吨以上.";

    public ChinaMapView(Context context) {
        super(context);
        this.context = context;
    }

    public void setOnChoseProvince(onProvinceClickLisener lisener) {
        this.onProvinceClickLisener = lisener;
    }

    //初始化准备工作
    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setTextSize(PublicViewUtils.getPxInt(12, context));
        rectPaint.setAntiAlias(true);

        //初始化省份内部画笔
        innerPaint = new Paint();
        innerPaint.setColor(Color.BLUE);
        innerPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(14);
        textPaint.setAntiAlias(true);

        headerTextPaint = new Paint();
        headerTextPaint.setColor(Color.WHITE);
        headerTextPaint.setTextSize(PublicViewUtils.getPxInt(6.5f, context));
        headerTextPaint.setAntiAlias(true);

        bottomTextPaint = new TextPaint();
        bottomTextPaint.setColor(Color.WHITE);
        bottomTextPaint.setTextSize(PublicViewUtils.getPxInt(6.5f, context));
        bottomTextPaint.setAntiAlias(true);

        //初始化省份外框画笔
        outerPaint = new Paint();
        outerPaint.setColor(Color.GRAY);
        outerPaint.setAntiAlias(true);
        outerPaint.setStrokeWidth(1);
        outerPaint.setStyle(Paint.Style.STROKE);
        //初始化手势帮助类
        scrollScaleGestureDetector = new ScrollScaleGestureDetector(this, onScrollScaleGestureListener);
    }

    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不管高度的设置Mode是什么，直接把View的高度按照宽度适配的缩放倍数进行适配
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height1 = MeasureSpec.getSize(widthMeasureSpec);

        displayHeight = PublicViewUtils.getDisplayHeight(context);
        if (map != null) {
            map_scale = width / map.getMax_x();
        }
        int height = (int) (map.getMax_y() * map_scale);
        setMeasuredDimension(width, displayHeight);
    }

    public void setMap(ChinaMapModel map) {
        this.map = map;
        isFirst = true;
        invalidate();
    }

    public void chingeMapColors() {
        invalidate();
    }

    private void initHeader(Canvas canvas) {
        RectF rectF = new RectF(0, 0, PublicViewUtils.getPxInt(25, context), PublicViewUtils.getPxInt(20, context));// 设置个新的长方形
        canvas.drawRoundRect(rectF, 7, 7, rectPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawText("大涨50+", PublicViewUtils.getPxInt(30, context),
                PublicViewUtils.getPxInt(15, context), headerTextPaint);

        //上涨
//        canvas.drawRect(PublicViewUtils.getPxInt(80, context),0,
//                PublicViewUtils.getPxInt(105, context),  PublicViewUtils.getPxInt(20, context) , rectPaint);

        RectF rectF1 = new RectF(PublicViewUtils.getPxInt(80, context), 0, PublicViewUtils.getPxInt(105, context),
                PublicViewUtils.getPxInt(20, context));
        canvas.drawRoundRect(rectF1, 7, 7, rectPaint);//第二个参数是x半径，第三个参数是y半径

        canvas.drawText("大涨", PublicViewUtils.getPxInt(110, context),
                PublicViewUtils.getPxInt(15, context), headerTextPaint);

        canvas.drawRect(0, PublicViewUtils.getPxInt(25, context),
                PublicViewUtils.getPxInt(25, context), PublicViewUtils.getPxInt(45, context), rectPaint);
        canvas.drawText("平稳", PublicViewUtils.getPxInt(30, context),
                PublicViewUtils.getPxInt(40, context), headerTextPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //保证只在初次绘制的时候进行缩放适配
        initHeader(canvas);
        if (isFirst) {
            viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            //首先重置所有点的坐标，使得map适应屏幕大小
            if (map != null) {
                map_scale = viewWidth / map.getMax_x();
            }
            //缩放所有Path
            scalePoints(canvas, map_scale);
            isFirst = false;
        } else {
            //关联缩放和平移后的矩阵
            scrollScaleGestureDetector.connect(canvas);
            scrollScaleGestureDetector.setScaleMax(3);//最大缩放倍数
            scrollScaleGestureDetector.setScalemin(1);//最小缩放倍数
            //绘制Map
            drawMap(canvas);
        }

        if (map.getProvinceslist() != null && map.getProvinceslist().size() > 0) {
            for (int i = 0; i < map.getProvinceslist().size(); i++) {
                drawOneArea(canvas, textPaint, map.getProvinceslist().get(i));
            }
        }

        canvas.translate(0, map.getMax_y() + 200);

        StaticLayout staticLayout = new StaticLayout(bottomText, bottomTextPaint, PublicViewUtils.getDisplayWidth(context) - 40,
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

        staticLayout.draw(canvas);
//        canvas.drawText("",
//                PublicViewUtils.getPxInt(14, context), map.getMax_y() + 200, headerTextPaint);
    }

    //绘制整个Map
    private void drawMap(Canvas canvas) {
        if (map.getProvinceslist().size() > 0) {
            outerPaint.setStrokeWidth(1);
            //首先记录下点击的省份的下标，先把其他的省份绘制完，
            for (int i = 0; i < map.getProvinceslist().size(); i++) {
                if (map.getProvinceslist().get(i).isSelect()) {
                    selectPosition = i;
                } else {
                    //此时绘制其他省份，边框画笔的宽度为1
                    innerPaint.setColor(map.getProvinceslist().get(i).getColor());
                    outerPaint.setColor(map.getProvinceslist().get(i).getLinecolor());
                    for (Path p : map.getProvinceslist().get(i).getListpath()) {
                        canvas.drawPath(p, innerPaint);
                        canvas.drawPath(p, outerPaint);
                    }
                }
            }
            //再绘制点击所在的省份,此时画笔宽度设为2.5，以达到着重显示的效果
            innerPaint.setColor(map.getProvinceslist().get(selectPosition).getColor());
            outerPaint.setColor(map.getProvinceslist().get(selectPosition).getLinecolor());
            outerPaint.setStrokeWidth(2.5f);
            for (Path p : map.getProvinceslist().get(selectPosition).getListpath()) {
                canvas.drawPath(p, innerPaint);
                canvas.drawPath(p, outerPaint);
            }
        }
    }


    /**
     * 画文字
     *
     * @param pCanvas
     * @param textPaint
     * @param provinceModel
     */
    private void drawOneArea(Canvas pCanvas, Paint textPaint, ProvinceModel provinceModel) {
        RectF testRect = new RectF();
        if (provinceModel.getId().equals("CN-52")) {
            provinceModel.getListpath().get(1).computeBounds(testRect, true);
        } else {
            provinceModel.getListpath().get(0).computeBounds(testRect, true);
        }


        int padding = PublicViewUtils.getPxInt(8, context);
        int paddingLeft = 0;
        int paddingTop = 0;

        //贵州
        if (provinceModel.getId().equals("CN-52")) {
            paddingTop += PublicViewUtils.getPxInt(5, context);
            paddingLeft += PublicViewUtils.getPxInt(3, context);
        }

        //云南
        if (provinceModel.getId().equals("CN-53")) {
            paddingTop += PublicViewUtils.getPxInt(5, context);
        }

        //广西
        if (provinceModel.getId().equals("CN-45")) {
            paddingTop += PublicViewUtils.getPxInt(5, context);
            paddingLeft += PublicViewUtils.getPxInt(10, context);
        }

        //香港
        if (provinceModel.getId().equals("CN-91")) {
            paddingTop += PublicViewUtils.getPxInt(2, context);
            paddingLeft += PublicViewUtils.getPxInt(5, context);
        }

        //甘肃
        if (provinceModel.getId().equals("CN-62")) {
            paddingLeft += PublicViewUtils.getPxInt(22, context);
            paddingTop += PublicViewUtils.getPxInt(20, context);
        }

        //内蒙古
        if (provinceModel.getId().equals("CN-15")) {
//            paddingTop += 85;
            paddingTop += PublicViewUtils.getPxInt(40, context);
            paddingLeft -= PublicViewUtils.getPxInt(10, context);
        }

        //广东
        if (provinceModel.getId().equals("CN-44")) {
//            paddingTop -= 25;
            paddingTop -= PublicViewUtils.getPxInt(10, context);
            paddingLeft += PublicViewUtils.getPxInt(10, context);
        }

        //福建
        if (provinceModel.getId().equals("CN-35")) {
            paddingTop -= PublicViewUtils.getPxInt(10, context);
            paddingLeft += PublicViewUtils.getPxInt(5, context);
        }
        //浙江
        if (provinceModel.getId().equals("CN-33")) {
            paddingTop += PublicViewUtils.getPxInt(10, context);
            paddingLeft -= PublicViewUtils.getPxInt(5, context);
        }

        //陕西
        if (provinceModel.getId().equals("CN-61")) {
            paddingTop += PublicViewUtils.getPxInt(15, context);
            paddingLeft += PublicViewUtils.getPxInt(5, context);
        }
        //河北
        if (provinceModel.getId().equals("CN-13")) {
            paddingTop += PublicViewUtils.getPxInt(15, context);
            paddingLeft -= PublicViewUtils.getPxInt(5, context);
        }
        //黑龙江
        if (provinceModel.getId().equals("CN-23")) {
            paddingLeft -= PublicViewUtils.getPxInt(5, context);
            paddingTop += PublicViewUtils.getPxInt(15, context);
        }

        //重庆
        if (provinceModel.getId().equals("CN-50")) {
            paddingTop += PublicViewUtils.getPxInt(5, context);
        }

        //安徽
        if (provinceModel.getId().equals("CN-34")) {
            paddingTop += PublicViewUtils.getPxInt(5, context);
            paddingLeft += PublicViewUtils.getPxInt(3, context);
        }

        //宁夏
        if (provinceModel.getId().equals("CN-64")) {
            paddingTop += PublicViewUtils.getPxInt(2, context);
            paddingLeft += PublicViewUtils.getPxInt(2, context);
        }

        //江苏
        if (provinceModel.getId().equals("CN-32")) {
            paddingLeft += PublicViewUtils.getPxInt(5, context);
        }

        //澳门
        if (provinceModel.getId().equals("CN-92")) {
            paddingTop += 720;
            paddingLeft += 680;
        }

        pCanvas.drawText(provinceModel.getName(), testRect.left + testRect.width() / 2 - padding + paddingLeft, testRect.top + testRect.height() / 2 + paddingTop, textPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scrollScaleGestureDetector.onTouchEvent(event);
    }

    //第一次绘制，缩小map到View指定大小
    private void scalePoints(Canvas canvas, float scale) {
        if (map.getProvinceslist().size() > 0)
            //map的左右上下4个临界点
            map.setMax_x(map.getMax_x() * scale);
        map.setMin_x(map.getMin_x() * scale);
        map.setMax_y(map.getMax_y() * scale);
        map.setMin_y(map.getMin_y() * scale);
        for (ProvinceModel province : map.getProvinceslist()) {
            innerPaint.setColor(province.getColor());
            List<Region> regionList = new ArrayList<>();
            List<Path> pathList = new ArrayList<>();
            for (Path p : province.getListpath()) {
                //遍历Path中的所有点，重置点的坐标
                Path newpath = resetPath(p, scale, regionList);
                pathList.add(newpath);
                canvas.drawPath(newpath, innerPaint);
                canvas.drawPath(newpath, outerPaint);
            }
            province.setListpath(pathList);
            //判断点是否在path画出的区域内
            province.setRegionList(regionList);
        }
    }

    private Path resetPath(Path path, float scale, List<Region> regionList) {
        List<PointF> list = new ArrayList<>();
        PathMeasure pathmesure = new PathMeasure(path, true);
        float[] s = new float[2];
        //按照缩放倍数重置Path内的所有点
        for (int i = 0; i < pathmesure.getLength(); i = i + 2) {
            pathmesure.getPosTan(i, s, null);
            PointF p = new PointF(s[0] * scale, s[1] * scale);
            list.add(p);
        }
        //重绘缩放后的Path
        Path path1 = new Path();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                path1.moveTo(list.get(i).x, list.get(i).y);
            } else {
                path1.lineTo(list.get(i).x, list.get(i).y);
            }
        }
        path1.close();
        //构造Path对应的Region,用于判断点击的点是否在Path内
        RectF rf = new RectF();
        Region re = new Region();
        path1.computeBounds(rf, true);
        re.setPath(path1, new Region((int) rf.left, (int) rf.top, (int) rf.right, (int) rf.bottom));
        regionList.add(re);
        return path1;
    }

    //选中所点击的省份
    public interface onProvinceClickLisener {
        void onChose(String provincename);
    }
}
