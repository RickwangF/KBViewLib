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
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;

import java.util.HashMap;
import java.util.Map;

public class NewsVideoPlayer extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mCoverOriginUrl;

    Drawable mDefaultRes;

    public NewsVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public NewsVideoPlayer(Context context) {
        super(context);
    }

    public NewsVideoPlayer(Context context, AttributeSet attrs) {
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

    @Override
    public int getLayoutId() {
        return R.layout.news_video_player;
    }

    private Drawable initCoverDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        int width = ScreenUtil.getScreenWidth(getContext());
        int heigth = Math.round(width * 9.0f / 16);
        drawable.setSize(width, heigth);
        drawable.setCornerRadius(4);
        drawable.setColor(Color.argb(1, 153, 153, 153));
        return drawable;
    }

    public void loadCoverImage(String url) {
        if(mCoverImage!=null) {
            if(!TextUtils.isEmpty(url)) {
                mCoverOriginUrl = url;
                mDefaultRes = initCoverDrawable();
                Glide.with(mCoverImage).load(url).placeholder(mDefaultRes).error(mDefaultRes).into(mCoverImage);
            }else{
                mCoverImage.setImageDrawable(mDefaultRes);
            }
        }
    }

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


    @Override
    public boolean setUp(String url, boolean cacheWithPlay, String title) {
        Map<String, String> header = new HashMap<>();
        return super.setUp(url, false, null, header, title);
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        NewsVideoPlayer sampleCoverVideo = (NewsVideoPlayer) gsyBaseVideoPlayer;
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl);
        return gsyBaseVideoPlayer;
    }


}
