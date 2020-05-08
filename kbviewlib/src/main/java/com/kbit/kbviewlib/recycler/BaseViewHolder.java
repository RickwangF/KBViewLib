package com.kbit.kbviewlib.recycler;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(BaseRecyclerAdapter adapter, int position) {

    }
    public Context getContext(){
        return itemView.getContext();
    }
}

