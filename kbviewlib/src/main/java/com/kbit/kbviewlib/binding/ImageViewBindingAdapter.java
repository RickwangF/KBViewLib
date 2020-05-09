package com.kbit.kbviewlib.binding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageViewBindingAdapter {
    @BindingAdapter(value = {"app:image_url", "app:place_holder", "app:error_image"}, requireAll = false)
    public static void loadImage(ImageView imageView, String imageUrl, Drawable placeHolder, Drawable error) {
        Glide.with(imageView).load(imageUrl).placeholder(placeHolder).error(error).into(imageView);
    }
}
