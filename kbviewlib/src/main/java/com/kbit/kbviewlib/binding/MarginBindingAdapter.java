package com.kbit.kbviewlib.binding;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

import com.kbit.kbbaselib.util.DeviceUtil;
import com.kbit.kbbaselib.util.DisplayUtil;

public class MarginBindingAdapter {

    @BindingAdapter(value = {"marginLeft", "marginTop", "marginRight", "marginBottom"}, requireAll = false)
    public static void setMargin(View view, float left, float top, float right, float bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(Math.round(left * DisplayUtil.getDensity()),
                Math.round(top * DisplayUtil.getDensity()),
                Math.round(right * DisplayUtil.getDensity()),
                Math.round(bottom * DisplayUtil.getDensity()));
        view.setLayoutParams(layoutParams);
    }
}
