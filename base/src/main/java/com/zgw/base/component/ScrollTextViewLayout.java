package com.zgw.base.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zgw.base.util.PublicViewUtils;
import com.zgw.base.R;

import java.util.List;

public class ScrollTextViewLayout extends RelativeLayout implements View.OnClickListener {
    /**
     * 实现textview 上下滚动。 只有一行Text不滚动，2行以上滚动，反复交替显示。
     *
     * @author
     */

    private int mDuration = 300;
    private TextView mTextView;
    private int mTextHight;
    private int mIndex = 0;
    private Context context;


    private ImageView turnImage;


    private String qmcAnalyseString = "";

    private String actionTitle = "";

    private boolean isLastScroll = true; //为了处理奖金提示不滚动添加的标志位 2017-06-05

    private String analyseMark = "";

    public void setIsLastScroll(boolean isLastScroll) {
        this.isLastScroll = isLastScroll;
    }

    private boolean bonusFlag = false;

    public void setQmcAnalyseString(String qmcAnalyseString) {
        this.qmcAnalyseString = qmcAnalyseString;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }


    public void setAnalyseMark(String analyseMark) {
        this.analyseMark = analyseMark;
    }

    private List<String> msgBeanList;

    public ScrollTextViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.scrollText);
        mDuration = a.getInteger(R.styleable.scrollText_animationDuration, 300);
        a.recycle();
    }

    public void setMsgBeanList(List<String> msgBeanList) {
        try {
            this.msgBeanList = msgBeanList;
            bonusFlag = false;
            if (msgBeanList == null) return;
            if (msgBeanList.size() < 2) {
                mTextView.setText(Html.fromHtml(msgBeanList.get(0)));
                mLinkUrl = msgBeanList.get(0);
                actionTitle = msgBeanList.get(0);
//                addTurnImage();
            } else {
                ShowAnimation();
            }
            setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTurnImage() {
//		if(context instanceof GoldLottery) return;
        removeTurnImage();
        turnImage = new ImageView(context);
        turnImage.setBackgroundResource(R.drawable.scroll_buy_lottery_guide);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                PublicViewUtils.getPxInt(11, context), PublicViewUtils.getPxInt(18, context));
        params.leftMargin = PublicViewUtils.getPxInt(5, context);
        params.rightMargin = PublicViewUtils.getPxInt(5, context);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		params.gravity =Gravity.CENTER_VERTICAL|Gravity.RIGHT;
//		setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        if (TextUtils.isEmpty(mLinkUrl)) {
            removeTurnImage();
        } else {
            this.addView(turnImage, params);
        }
    }

    private String mLinkUrl = "";


    /**
     * 获取跳转地址
     */
    private void getLinkUrl(String[] textArr) {
        String[] mLinkUrlArray = new String[textArr.length];
        String[] mScrollStringArray = new String[textArr.length];
        for (int i = 0; i < textArr.length; i++) {
            if (TextUtils.isEmpty(textArr[i])) break;
            if (textArr[i].contains("{")) {
                String[] temp = textArr[i].split("\\{");
                if (temp != null && temp.length != 0) {
                    mScrollStringArray[i] = temp[0];
                    mLinkUrlArray[i] = temp[1].replace("}", "");
                }
            } else {
                mScrollStringArray[i] = textArr[i];
            }
        }
    }


    private void removeTurnImage() {
        if (turnImage != null) {
            this.removeView(turnImage);
            turnImage = null;
        }
    }

    public int getDuration() {
        return mDuration;
    }

    public void setTextColor(int id) {
        mTextView.setTextColor(id);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) this.getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mTextHight = mTextView.getHeight();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    animationOpen();
                    break;
                case 2:
                    animationClose();
                    break;
            }

        }

    };

    protected void animationOpen() {
        post(show);
    }

    protected void animationClose() {
        post(dismiss);
    }

    Runnable show = new Runnable() {

        @Override
        public void run() {
            ShowAnimation();
        }

    };

    private TranslateAnimation animation = null;

    private void ShowAnimation() {
        int fromYDelta = 0, toYDelta = 0;
        fromYDelta = mTextHight;
        animation = new TranslateAnimation(0, 0, fromYDelta, toYDelta);
        animation.setDuration(mDuration);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                if (msgBeanList == null) return;
                if (msgBeanList.size() > 1) {
                    mHandler.postDelayed(dismiss, 3000);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {
                if (msgBeanList == null) return;
                mIndex = mIndex % msgBeanList.size();
                mTextView.setText(Html.fromHtml(msgBeanList.get(mIndex)));
                if (msgBeanList.get(mIndex) != null) {
                    mLinkUrl = msgBeanList.get(mIndex);
                }
                actionTitle = msgBeanList.get(mIndex);
//                addTurnImage();
                mTextView.setVisibility(View.VISIBLE);
                mIndex += 1;
            }

        });
        startAnimation(animation);
    }

    private void disMissAnimation() {
        int fromYDelta = 0, toYDelta = 0;
//		toYDelta = -1 * mTextHight;
//		fromYDelta = mTextHight;
        toYDelta = -1 * mTextHight;
        fromYDelta = 0;
        TranslateAnimation animation = new TranslateAnimation(0, 0,
                fromYDelta, toYDelta);
        animation.setDuration(mDuration);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                int left = mTextView.getLeft();
                int top = mTextView.getTop() - mTextHight;
                int width = mTextView.getWidth();
                int height = mTextView.getHeight();
                mTextView.clearAnimation();
                mTextView.layout(left, top, left + width, top + height);
                ShowAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {
//				mHandler.postDelayed(show, mDuration);
//				mTextView.setVisibility(View.INVISIBLE);
                removeTurnImage();
            }

        });
        startAnimation(animation);
    }

    Runnable dismiss = new Runnable() {

        @Override
        public void run() {
            if (isLastScroll) {
                disMissAnimation();
            }
        }

    };

    @Override
    public void onClick(View view) {
        try {
            if (TextUtils.isEmpty(mLinkUrl)) return;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
