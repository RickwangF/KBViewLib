package com.kbit.kbviewlib.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.kbit.kbviewlib.R;

public class MTToolBar extends Toolbar {

    private TextView mTitleView;
    private CharSequence mTitleText;

    public MTToolBar(Context context) {
        super(context);
    }

    public MTToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MTToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ensureTitleView();
    }

    @Override
    public void setTitle(CharSequence title) {
        ensureTitleView();
        mTitleView.setText(title);
        mTitleText = title;
    }

    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    @Override
    public void setTitleTextColor(int color) {
        ensureTitleView();
        mTitleView.setTextColor(color);
    }

    private void ensureTitleView() {
        if (mTitleView == null) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.middle_title_toolbar, this, true);
            mTitleView = v.findViewById(R.id.toolbar_title);
        }
    }
}
