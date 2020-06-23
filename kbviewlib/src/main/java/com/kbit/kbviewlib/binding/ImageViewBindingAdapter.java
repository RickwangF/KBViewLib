package com.kbit.kbviewlib.binding;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kbit.kbbaselib.util.StringUtil;

public class ImageViewBindingAdapter {
    @BindingAdapter(value = {"app:image_url", "app:image_res", "app:place_holder", "app:error_image", "app:is_round"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imageUrl, Drawable resource, Drawable placeHolder, Drawable error, boolean isRound) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeHolder != null) {
            requestOptions = requestOptions.placeholder(placeHolder);
        }
        if (error != null) {
            requestOptions = requestOptions.error(error);
        }
        if (isRound) {
            requestOptions = requestOptions.circleCrop();
        }

        if (!StringUtil.isEmpty(imageUrl)) {
            Glide.with(imageView).load(imageUrl).apply(requestOptions).into(imageView);
        } else if (resource != null) {
            Glide.with(imageView).load(resource).apply(requestOptions).into(imageView);
        }
    }
}
