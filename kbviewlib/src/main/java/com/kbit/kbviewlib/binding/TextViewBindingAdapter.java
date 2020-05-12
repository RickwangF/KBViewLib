package com.kbit.kbviewlib.binding;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.kbit.kbbaselib.util.DateUtil;

import java.util.Date;

public class TextViewBindingAdapter {

    @BindingAdapter(value = { "app:dateFormat" })
    public static void dateFormat(TextView textView, long timestamp) {
        Date date = DateUtil.getDateFromTimeStamp(timestamp, true);
        String dateString = DateUtil.getDateStringFromDate(date, "yyyy-MM-dd HH:mm:ss");
        textView.setText(dateString);
    }

    @BindingAdapter(value = { "app:friendlyFormat" })
    public static void friendlyFormat(TextView textView, long timestamp) {
        Date date = DateUtil.getDateFromTimeStamp(timestamp, true);
        String dateString = DateUtil.getRelativeTimeStringFromNow(date);
        textView.setText(dateString);
    }

}
