package com.example.fintonicbeer.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fintonicbeer.R
import java.lang.StringBuilder

/**
 *
 */
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_beer_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("android:textList")
fun TextView.setPairList(list: List<String>?) {
    val str = StringBuilder("")
        list?.forEach { it ->
            if (str.isEmpty()) {
                str.append("\u25CF" + "\u0009" + it)
            } else {
                str.append("\n" + "\u25CF" + "\u0009" + it)
            }
        }

    this.text = str
}