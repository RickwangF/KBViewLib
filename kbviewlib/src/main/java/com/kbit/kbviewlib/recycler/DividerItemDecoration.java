package com.kbit.kbviewlib.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "DividerItemDecoration";

    @Dimension
    private int dividerHeight;

    private Drawable mDivider;

    private boolean isShowFirstDivider = false;

    private boolean isShowLastDivider = false;

    private int mOffsetCount = 0;

    public DividerItemDecoration(Context context) {
        this(context, false);
    }

    public DividerItemDecoration(@Dimension float dividerHeight) {
        this(dividerHeight,true);
    }

    public DividerItemDecoration(@Dimension float dividerHeight, boolean isShowLastDivider) {
        this.dividerHeight = Math.round(dividerHeight);
        this.isShowLastDivider = isShowLastDivider;
    }

    /**
     * @param dividerHeight 分隔线高度
     * @param color         分隔线颜色
     */
    public DividerItemDecoration(@Dimension float dividerHeight, @ColorInt int color) {
        this.dividerHeight = Math.round(dividerHeight);
        mDivider = new ColorDrawable(color);
    }

    /**
     *
     * @param context
     * @param isShowLastDivider true 绘制最后一个item的分隔线
     */
    public DividerItemDecoration(Context context, boolean isShowLastDivider) {
        final TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
        dividerHeight = mDivider.getIntrinsicHeight();
        this.isShowLastDivider = isShowLastDivider;
    }

    public void showFirstDivider(boolean show) {
        isShowFirstDivider = show;
    }

    public void showLastDivider(boolean show) {
        isShowLastDivider = show;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mDivider == null) {
            return;
        }
        if (dividerHeight <= 0) {
            return;
        }
        final int childSize = parent.getChildCount();
        View child;
        RecyclerView.LayoutParams lp;
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) parent.getAdapter();
        if (adapter == null) {
            return;
        }
        for (int i = 0; i < childSize; i++) {
            child = parent.getChildAt(i);
            lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (lm instanceof GridLayoutManager) {
                drawVertical(c, child, lp);
                int spanCount=((GridLayoutManager) lm).getSpanCount();
                int sc = ((GridLayoutManager.LayoutParams) lp).getSpanSize();
                if (sc != spanCount) {
                    drawHorizontal(c, child, lp);
                }
            } else if (lm instanceof LinearLayoutManager) {
                LinearLayoutManager llm = (LinearLayoutManager) lm;
                if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    drawVertical(c, child, lp);
                } else {
                    drawHorizontal(c, child, lp);
                }

            }

        }
    }

    private void drawVertical(Canvas c, View child, RecyclerView.LayoutParams lp) {
        final int top = child.getTop() - lp.topMargin;
        final int bottom = child.getBottom() + lp.bottomMargin + dividerHeight;
        final int left = child.getRight() + lp.rightMargin;
        final int right = left + dividerHeight;
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }

    private void drawHorizontal(Canvas c, View child, RecyclerView.LayoutParams lp) {
        final int position = lp.getViewAdapterPosition();
        final int top = child.getBottom() + lp.bottomMargin;
        final int bottom = top + dividerHeight;
        final int left = child.getLeft() - lp.leftMargin;
        final int right = child.getRight() + lp.rightMargin + dividerHeight;
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
        // if ((includeEdge && position == 0) || (position == 0 && mShowStartDivider)) {
        // mDivider.setBounds(left, child.getTop() - dividerHeight, right,
        // child.getTop());
        // mDivider.draw(c);
        // }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewAdapterPosition();

        if (position < 0) {
            return;
        }
        if (dividerHeight <= 0) {
            return;
        }
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) parent.getAdapter();
        final int lastItemIndex = adapter.getItemCount() - 1;
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            GridLayoutManager gm = (GridLayoutManager) lm;
            int spanCount = gm.getSpanCount();
            int spanSize = gm.getSpanSizeLookup().getSpanSize(position);
            Log.d(TAG, "position " + position + " spanSize " + spanSize + " spanCount " + spanCount+" mOffsetCount "+mOffsetCount);
            if (spanSize != spanCount) {
                gridItemOffsets(outRect, position - mOffsetCount, spanCount);
            } else {
                if (position == 0) {
                    mOffsetCount = 0;
                }
                if(adapter instanceof WrapperRecyclerViewAdapter)
                    if (!((WrapperRecyclerViewAdapter)adapter).isFooterPosition(position)) {
                        mOffsetCount++;
                    }
            }
        } else if (lm instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sgm = (StaggeredGridLayoutManager) lm;
            final int spanCount = sgm.getSpanCount();
            gridItemOffsets(outRect, position, spanCount);
        } else {
            LinearLayoutManager llm = (LinearLayoutManager) lm;
            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (position > 0) {
                    outRect.left = dividerHeight;
                }
            } else {
                // if ((includeEdge && position == 0) || (position == 0 && mShowStartDivider)) {
                // outRect.top = dividerHeight;
                // }
                if (!isShowLastDivider && position >= lastItemIndex) {//最后一个item不画分隔线
                    return;
                }
                outRect.bottom = dividerHeight;
            }
        }

    }

    private void gridItemOffsets(Rect outRect, int position, int spanCount) {

        int column = position % spanCount; // item column

        if (isShowLastDivider) {
            outRect.left = dividerHeight - column * dividerHeight / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * dividerHeight / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = dividerHeight;
            }
            outRect.bottom = dividerHeight; // item bottom
        } else {
            outRect.left = column * dividerHeight / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = dividerHeight - (column + 1) * dividerHeight / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = dividerHeight; // item top
            }
        }

    }
}
