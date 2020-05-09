package com.example.kbviewlib;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.kbviewlib.databinding.ItemTestBinding;
import com.kbit.kbviewlib.recycler.BaseRecyclerAdapter;

import java.util.List;

public class TestAdapter extends BaseRecyclerAdapter<TestModel, TestViewHolder> {

    public TestAdapter(@NonNull Context context, List<? extends TestModel> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTestBinding bind = ItemTestBinding.inflate(mInflater, parent, false);
        return new TestViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.onBind(this, position);
    }
}
