package com.kbit.kbviewlib.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context mContext;
    private final Object mLock = new Object();
    private ArrayList<T> mObjects;
    WrapperRecyclerView.OnItemClickListener mItemClickListener;
    WrapperRecyclerView.OnItemLongClickListener mItemLongClickListener;
    boolean mNotifyOnChange = true;
    public LayoutInflater mInflater;

    BaseRecyclerAdapter() {
    }

    public BaseRecyclerAdapter(@NonNull Context context, List<? extends T> data) {
        mObjects = data == null ? new ArrayList<T>(0) : new ArrayList<>(data);
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(WrapperRecyclerView.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(WrapperRecyclerView.OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    public RecyclerView.ViewHolder onCreateHeaderViewHolder(View view) {
        return new BaseViewHolder(view) {
        };
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(View view) {
        return new BaseViewHolder(view) {
        };
    }

    public void setData(Collection<? extends T> data) {
        synchronized (mLock) {
            mObjects.clear();
            if (data != null) {
                mObjects.addAll(data);
                if (mNotifyOnChange) notifyDataSetChanged();
            }

        }
    }

    public void add(@Nullable T object) {
        synchronized (mLock) {
            mObjects.add(object);
            if (mNotifyOnChange)
                notifyItemInserted(mObjects.size() - 1);
        }
    }

    public void add(int index, @Nullable T object) {
        synchronized (mLock) {
            mObjects.add(index, object);
            if (mNotifyOnChange)
                notifyItemInserted(index);
        }
    }

    public void setData(int index, @Nullable T object) {
        synchronized (mLock) {
            mObjects.set(index, object);
            if (mNotifyOnChange)
                notifyItemChanged(index);
        }
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection == null) {
            return;
        }
        synchronized (mLock) {
            int count = mObjects.size();
            mObjects.addAll(collection);
            if (mNotifyOnChange) notifyItemRangeInserted(count, collection.size());
        }
    }

    public void addAll(T... items) {
        if (items == null) {
            return;
        }
        synchronized (mLock) {
            int count = mObjects.size();
            Collections.addAll(mObjects, items);
            if (mNotifyOnChange) notifyItemChanged(count, items.length);
        }
    }

    public void addAll(int index, Collection<? extends T> collection) {
        if (collection == null) {
            return;
        }
        synchronized (mLock) {
            mObjects.addAll(index, collection);
            if (mNotifyOnChange) notifyItemRangeInserted(index, collection.size());
        }
    }

    public void insert(int index, @Nullable T object) {
        synchronized (mLock) {
            mObjects.add(index, object);
            if (mNotifyOnChange) notifyItemInserted(index);
        }
    }

    public void remove(@Nullable T object) {
        synchronized (mLock) {
            int index = mObjects.indexOf(object);
            if (index >= 0) {
                mObjects.remove(index);
                if (mNotifyOnChange) notifyItemRemoved(index);
            }
        }
    }

    public T remove(int index) {
        synchronized (mLock) {
            if (index < 0) {
                return null;
            }
            T data = mObjects.remove(index);
            notifyItemRemoved(index);
            return data;
        }
    }

    public void move(int fromPosition, int toPosition) {
        synchronized (mLock) {
            Collections.swap(mObjects, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) + 1);
        }
    }

    public ArrayList<T> getData() {
        return mObjects;
    }

    public void clear() {
        synchronized (mLock) {
            mObjects.clear();
            if (mNotifyOnChange) notifyDataSetChanged();
        }
    }

    public int indexOf(T data) {
        return mObjects.indexOf(data);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public T getItem(int position) {
        if (position >= getItemCount()) {
            return null;
        }
        return mObjects.get(position);
    }

    public View inflate(@LayoutRes int resource, @NonNull ViewGroup root) {
        return mInflater.inflate(resource, root, false);
    }

    public Context getContext() {
        return mContext;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }
}
