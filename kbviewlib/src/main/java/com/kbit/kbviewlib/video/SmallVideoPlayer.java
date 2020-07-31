package com.kbit.kbviewlib.video;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.bumptech.glide.Glide;
import com.kbit.kbviewlib.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.Map;

public class SmallVideoPlayer extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mCoverOriginUrl;

    Drawable mDefaultRes;

    public SmallVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SmallVideoPlayer(Context context) {
        super(context);
    }

    public SmallVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = findViewById(R.id.thumbImage);
        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.setVisibility(VISIBLE);
        }
    }

    private Drawable initCoverDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        int width = ScreenUtil.getScreenWidth(getContext());
        int heigth = Math.round(width * 16f / 9);
        drawable.setSize(width, heigth);
        drawable.setCornerRadius(4);
        drawable.setColor(Color.argb(1, 0, 0, 0));
        return drawable;
    }


    public void loadCoverImage(String url) {
        if (mCoverImage != null) {
            if (!TextUtils.isEmpty(url)) {
                mCoverOriginUrl = url;
                mDefaultRes = initCoverDrawable();
                Glide.with(mCoverImage).load(url).placeholder(mDefaultRes).error(mDefaultRes).into(mCoverImage);
            } else {
                mCoverImage.setImageDrawable(mDefaultRes);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.small_video_player;
    }

//    @Override
//    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
//        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
//        //不给触摸快进，如果需要，屏蔽下方代码即可
//        mChangePosition = false;
//
//        //不给触摸音量，如果需要，屏蔽下方代码即可
//        mChangeVolume = false;
//
//        //不给触摸亮度，如果需要，屏蔽下方代码即可
//        mBrightness = false;
//    }

    @Override
    protected void updateStartImage() {
        super.updateStartImage();
        if (mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.video_pause_selector);
            } else {
                imageView.setImageResource(R.drawable.video_play_selector);
            }
        }
    }

//    @Override
//    protected void touchDoubleUp() {
//        //super.touchDoubleUp();
//        //不需要双击暂停
//    }

    @Override
    public boolean setUp(String url, boolean cacheWithPlay, String title) {
        Map<String, String> header = new HashMap<>();
        return super.setUp(url, false, null, header, title);
    }
}
