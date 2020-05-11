package com.kbit.kbviewlib.binding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

public class MarginBindingAdapter {

    @BindingAdapter(value = {"marginLeft", "marginTop", "marginRight", "marginBottom"}, requireAll = false)
    public static void setMargin(View view, float left, float top, float right, float bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(Math.round(left), Math.round(top), Math.round(right), Math.round(bottom));
        view.setLayoutParams(layoutParams);
    }
}
