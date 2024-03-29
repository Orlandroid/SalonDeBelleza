package com.example.citassalon.presentacion.features.extensions

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.citassalon.R


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.changeDrawableColor(color: Int) {
    this.setColorFilter(resources.getColor(color))
}

fun ImageView.getImageLikeBitmap(): Bitmap {
    return (this.drawable as BitmapDrawable).bitmap
}

fun ImageView.loadImage(urlImage: String) {
    Glide.with(context).load(urlImage).placeholder(R.drawable.loading_animation)
        .transition(DrawableTransitionOptions.withCrossFade()).circleCrop().into(this)
}


fun <T> ImageView.getBitmap(onSuccess: (image: Bitmap) -> Unit, image: T) {
    Glide.with(context)
        .asBitmap()
        .load(image)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                this@getBitmap.setImageResource(R.drawable.ic_baseline_broken_image_24)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onSuccess(resource)
            }
        })
}


fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}

fun View.getColor(@ColorRes color: Int): Int {
    return this.context.resources.getColor(color)
}

fun View.changeDrawableColor(@ColorRes drawableColor: Int) {
    if (this.background == null) {
        Log.e("Error", "backgroundDrawable is null")
        return
    }
    background.setColorFilter(resources.getColor(drawableColor), PorterDuff.Mode.SRC_IN)
}

fun View.getPackageName(): String {
    return context?.applicationContext?.packageName.toString()
}



