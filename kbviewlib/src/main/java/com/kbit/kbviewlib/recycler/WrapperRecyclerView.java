package com.kbit.kbviewlib.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class WrapperRecyclerView extends RecyclerView {
    private WrapperRecyclerViewAdapter mWrapperAdapter;
    private BaseRecyclerAdapter mAdapter;
    private OnItemClickListener mItemClickListener;
    private LayoutInflater mInflater;

    public WrapperRecyclerView(Context context) {
        this(context,null);
    }

    public WrapperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WrapperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(getContext());
    }

    public void addHeaderView(@LayoutRes int layoutId, Object data, boolean isSelectable) {
        addHeaderView(mInflater.inflate(layoutId, this, false), data, isSelectable);
    }

    /**
     * add header view
     */
    public void addHeaderView(View view, Object data, boolean isSelectable) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.addHeaderView(view, data, isSelectable);
        }
    }

    public View addHeaderView(@LayoutRes int layoutId) {
        View view = mInflater.inflate(layoutId, this, false);
        addHeaderView(view);
        return view;
    }

    /**
     * add header view
     */
    public void addHeaderView(View view) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.addHeaderView(view);
        }
    }

    /**
     * delete header view
     */
    public void removeHeaderView(View view) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.removeHeaderView(view);
        }
    }

    /**
     * remove header view
     */
    public void removeAllHeaderView() {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.removeAllHeaderView();
        }
    }

    public void addFooterView(View view, Object data, boolean isSelectable) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.addFooterView(view, data, isSelectable);
        }
    }

    /**
     * add footer view
     *
     * @param view
     */
    public void addFooterView(View view) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.addFooterView(view);
        }
    }

    /**
     * delete footer view
     *
     * @param view
     */
    public void removeFooterView(View view) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.removeFooterView(view);
        }
    }

    public WrapperRecyclerViewAdapter getAdapter() {
        return mWrapperAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof BaseRecyclerAdapter) {
            if (mAdapter != null) {
                mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
            }
            if (adapter instanceof WrapperRecycleListAdapter) {
                mWrapperAdapter = (WrapperRecyclerViewAdapter) adapter;
            } else {
                mWrapperAdapter = new WrapperRecyclerViewAdapter((BaseRecyclerAdapter) adapter);
            }
            mAdapter = mWrapperAdapter.getWrapperAdapter();
            mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(mWrapperAdapter);
            mWrapperAdapter.setOnItemClickListener(mItemClickListener);
        }else {
            super.setAdapter(adapter);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (mWrapperAdapter != null) {
            mWrapperAdapter.setOnItemClickListener(listener);
        } else {
            mItemClickListener = listener;
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mWrapperAdapter.setOnItemLongClickListener(listener);
    }

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            mWrapperAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeChanged(mWrapperAdapter.getHeaderCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapperAdapter.notifyItemRangeChanged(mWrapperAdapter.getHeaderCount() + positionStart, itemCount,
                    payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeInserted(mWrapperAdapter.getHeaderCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapperAdapter.notifyItemRangeRemoved(mWrapperAdapter.getHeaderCount() + positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapperAdapter.notifyItemMoved(mWrapperAdapter.getHeaderCount() + fromPosition,
                    mWrapperAdapter.getHeaderCount() + toPosition);
        }
    };

    public interface OnLoadingMoreListener {
        void onLoadingMore();
    }

    public interface OnRefreshingListener {
        void onRefresh();
    }


    public interface OnItemClickListener {
        void onItemClick(BaseRecyclerAdapter<?, ViewHolder> adapter, View view, int position, int viewType);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseRecyclerAdapter<?, ViewHolder> adapter, View view, int position, int viewType);
    }
}
