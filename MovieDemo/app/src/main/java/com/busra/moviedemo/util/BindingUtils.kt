package com.busra.moviedemo.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setUrl(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}