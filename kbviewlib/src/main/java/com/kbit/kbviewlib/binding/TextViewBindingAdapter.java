package com.kbit.kbviewlib.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.kbit.kbbaselib.util.DateUtil;

import java.util.Date;

public class TextViewBindingAdapter {

    @BindingAdapter(value = { "app:timestamp" })
    public static void timestampDisplay(TextView textView, long timestamp) {
        Date date = DateUtil.getDateFromTimeStamp((int) timestamp, false);
        String dateString = DateUtil.getDateStringFromDate(date, "yyyy-MM-dd");
        textView.setText(dateString);
    }

}
