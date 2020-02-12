package com.zgw.video;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class VideoPlayer extends StandardGSYVideoPlayer {

    protected TextView speedBtn;

    private float speed = 1;

    protected ImageView loading;

    public VideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        speedBtn = findViewById(R.id.speedBtn);
        loading = findViewById(R.id.loading);

        speedBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveTypeUI();
            }
        });

        ((AnimationDrawable)loading.getBackground()).start();
//        resolveTypeUI();
    }

    /**
     * 显示比例
     * 注意，GSYVideoType.setShowType是全局静态生效，除非重启APP。
     */
    private void resolveTypeUI() {
        if (speed == 1) {
            speed = 1.5f;
        } else if (speed == 1.5f) {
            speed = 2f;
        } else if (speed == 2) {
            speed = 0.5f;
        } else if (speed == 0.5f) {
            speed = 0.25f;
        } else if (speed == 0.25f) {
            speed = 1;
        }
        speedBtn.setText(speed + "倍");
        setSpeedPlaying(speed, true);
    }
}
