package com.example.kbviewlib;

import android.view.View;

import com.example.kbviewlib.databinding.ItemTestBinding;
import com.kbit.kbviewlib.recycler.BaseRecyclerAdapter;
import com.kbit.kbviewlib.recycler.BaseViewHolder;

public class TestViewHolder extends BaseViewHolder {

    private ItemTestBinding mBinding;

    public TestViewHolder(ItemTestBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    @Override
    public void onBind(BaseRecyclerAdapter adapter, int position) {
        super.onBind(adapter, position);
        TestModel model = (TestModel) adapter.getItem(position);
        mBinding.setModel(model);
    }
}
