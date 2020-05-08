package com.kbit.kbviewlib.recycler;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class WrapperRecyclerViewAdapter  extends BaseRecyclerAdapter
        implements WrapperRecycleListAdapter {

    private static final String TAG = "WrapperRecyclerViewAdapter";

    private BaseRecyclerAdapter mAdapter;
    private SparseArray<FixedViewInfo> mHeaderViews;
    private SparseArray<FixedViewInfo> mFooterViews;

    private int TYPE_HEADER_VIEW = 20000000;

    private int TYPE_FOOTER_VIEW = 30000000;

    private static final int TYPE_REFRESH_VIEW = 10000000;

    private static final int TYPE_LOADING_VIEW = 40000000;

    public WrapperRecyclerViewAdapter(BaseRecyclerAdapter adapter) {
        super();
        mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    private int indexOfValue(SparseArray<FixedViewInfo> array, View view) {
        int size = array.size();

        for (int i = 0; i < size; i++) {
            FixedViewInfo o = array.valueAt(i);
            if (o.view == view) {
                return i;
            }
        }
        return -1;
    }

    public void addHeaderView(View view, Object data, boolean isSelectable) {
        int index = indexOfValue(mHeaderViews, view);
        if (index < 0) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = view;
            info.data = data;
            info.isSelectable = isSelectable;
            mHeaderViews.put(TYPE_HEADER_VIEW++, info);
            notifyItemInserted(mHeaderViews.size() - 1);
        }
    }

    public void addHeaderView(View view) {
        addHeaderView(view, null, false);
    }

    public void removeHeaderView(View view) {
        int index = indexOfValue(mHeaderViews, view);
        if (index >= 0) {
            mHeaderViews.removeAt(index);
            notifyItemRemoved(index);
        }
    }

    public void removeAllHeaderView() {
        int count = mHeaderViews.size();
        mHeaderViews.clear();
        if (mNotifyOnChange)
            notifyItemRangeRemoved(0, count);
    }

    public void addFooterView(View view, Object data, boolean isSelectable) {
        int index = indexOfValue(mFooterViews, view);
        if (index < 0) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = view;
            info.data = data;
            info.isSelectable = isSelectable;
            mFooterViews.put(TYPE_FOOTER_VIEW++, info);
            notifyItemInserted(getItemCount());
        }
    }

    public void addFooterView(View view) {
        addFooterView(view, null, false);
    }

    public void removeFooterView(View view) {
        int index = indexOfValue(mFooterViews, view);
        if (index >= 0) {
            mFooterViews.removeAt(index);
            notifyItemRemoved(mHeaderViews.size() + mAdapter.getItemCount() + index);
        }
    }

    public void setRefreshView(View view) {
        int index = indexOfValue(mHeaderViews, view);
        if (index < 0) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = view;
            mHeaderViews.put(TYPE_REFRESH_VIEW, info);
            notifyItemInserted(0);
        }
    }

    public void setLoadingMoreView(View view) {
        int index = indexOfValue(mFooterViews, view);
        if (index < 0 && view.getParent() == null) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = view;
            mFooterViews.put(TYPE_LOADING_VIEW, info);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private boolean isHeaderViewType(int viewType) {
        int index = mHeaderViews.indexOfKey(viewType);
        return index >= 0;
    }

    private boolean isFooterViewType(int viewType) {
        int index = mFooterViews.indexOfKey(viewType);
        return index >= 0;
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    public boolean isFooterPosition(int position) {
        return position >= mHeaderViews.size() + mAdapter.getItemCount();
    }

    private RecyclerView.ViewHolder onCreateHeaderViewHolder(FixedViewInfo info) {
        RecyclerView.ViewHolder vh = mAdapter.onCreateHeaderViewHolder(info.view);
        if (info.isSelectable) {
            setOnClick(vh);
        }
        return vh;
    }

    private RecyclerView.ViewHolder onCreateFooterViewHolder(FixedViewInfo info) {
        RecyclerView.ViewHolder vh = mAdapter.onCreateFooterViewHolder(info.view);
        if (info.isSelectable) {
            setOnClick(vh);
        }
        return vh;
    }

    private RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = mAdapter.onCreateViewHolder(parent, viewType);
        setOnClick(vh);
        return vh;
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    private void setOnClick(final RecyclerView.ViewHolder vh) {
        if (vh !=null && mItemClickListener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vh.getAdapterPosition() >= 0) {
                        long currentTime = Calendar.getInstance().getTimeInMillis();
                        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                            lastClickTime = currentTime;
                            mItemClickListener.onItemClick(WrapperRecyclerViewAdapter.this, v, vh.getAdapterPosition(), vh.getItemViewType());
                        }
                    }
                }
            });
        }
        if (vh !=null && mItemLongClickListener != null) {
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (vh.getAdapterPosition() >= 0) {
                        return mItemLongClickListener.onItemLongClick(WrapperRecyclerViewAdapter.this, v,
                                vh.getAdapterPosition(), vh.getItemViewType());
                    }
                    return false;
                }
            });
        }
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterPosition(position)) {
            int index = position - mHeaderViews.size() - mAdapter.getItemCount();
            return mFooterViews.keyAt(index);
        }
        return mAdapter.getItemViewType(position - mHeaderViews.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return onCreateHeaderViewHolder(mHeaderViews.get(viewType));
        } else if (isFooterViewType(viewType)) {
            return onCreateFooterViewHolder(mFooterViews.get(viewType));
        }
        return onCreateDefaultViewHolder(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderPosition(position)) {
            //mAdapter.onBindViewHolder(holder, position);
            return;
        } else if (isFooterPosition(position)) {
            //mAdapter.onBindViewHolder(holder, position);
            return;
        }

        // Adapter
        final int adjPosition = position - mHeaderViews.size();
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (isHeaderPosition(position)) {
            //mAdapter.onBindViewHolder(holder, position,payloads);
            return;
        } else if (isFooterPosition(position)) {
            //mAdapter.onBindViewHolder(holder, position,payloads);
            return;
        }
        // Adapter
        final int adjPosition = position - mHeaderViews.size();
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition, payloads);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderViews.size() + mAdapter.getItemCount() + mFooterViews.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) manager;
            GridLayoutManager.SpanSizeLookup lookup = glm.getSpanSizeLookup();
            if (!(lookup instanceof HeaderSpanSizeLookup)) {
                glm.setSpanSizeLookup(new HeaderSpanSizeLookup(recyclerView, lookup));
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) params;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public BaseRecyclerAdapter getWrapperAdapter() {
        return mAdapter;
    }

    @Override
    public Object getItem(int position) {
        //Header
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders) {
            return mHeaderViews.valueAt(position).data;
        }

        //adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItem(adjPosition);
            }
        }
        FixedViewInfo footer = mFooterViews.valueAt(adjPosition - adapterCount);
        //Footer
        return footer == null ? null : footer.data;
    }

    @Override
    public void setData(Collection data) {
        mAdapter.setData(data);
    }

    @Override
    public void add(@Nullable Object object) {
        mAdapter.add(object);
    }

    @Override
    public void add(int index, @Nullable Object object) {
        mAdapter.add(index, object);
    }

    @Override
    public void addAll(Collection collection) {
        mAdapter.addAll(collection);
    }

    @Override
    public void addAll(Object[] items) {
        mAdapter.addAll(items);
    }

    @Override
    public void addAll(int index, Collection collection) {
        mAdapter.addAll(index, collection);
    }

    @Override
    public void insert(int index, @Nullable Object object) {
        mAdapter.insert(index, object);
    }

    @Override
    public void remove(@Nullable Object object) {
        mAdapter.remove(object);
    }

    @Override
    public Object remove(int index) {
        if (index < 0) {
            return null;
        }
        //Header
        int numHeaders = mHeaderViews.size();
        //adapter
        final int adjPosition = index - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.remove(adjPosition);
            }
        }
        return null;

    }

    @Override
    public void clear() {
        mAdapter.clear();
    }

    @Override
    public int indexOf(Object data) {
        return mAdapter.indexOf(data);
    }

    @Override
    public View inflate(@LayoutRes int resource, @NonNull ViewGroup root) {
        return mAdapter.inflate(resource, root);
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
        mAdapter.setNotifyOnChange(notifyOnChange);
    }

    private static class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private RecyclerView mRecyclerView;
        private GridLayoutManager.SpanSizeLookup oldLookup;

        private HeaderSpanSizeLookup(RecyclerView recyclerView, GridLayoutManager.SpanSizeLookup lookup) {
            mRecyclerView = recyclerView;
            oldLookup = lookup;
        }

        @Override
        public int getSpanSize(int position) {
            WrapperRecyclerViewAdapter adapter = (WrapperRecyclerViewAdapter) mRecyclerView.getAdapter();
            GridLayoutManager manager = (GridLayoutManager) mRecyclerView.getLayoutManager();
            return adapter.isHeaderPosition(position) || adapter.isFooterPosition(position)
                    ? manager.getSpanCount()
                    : oldLookup.getSpanSize(position - adapter.getHeaderCount());
        }
    }

    public class FixedViewInfo {
        public View view;
        public Object data;
        public boolean isSelectable;
    }
}
