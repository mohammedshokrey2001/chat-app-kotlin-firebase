package com.example.chatapp.ui.binding_adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load


@BindingAdapter("bind_image_url_blur", requireAll = true)
fun ImageView.setImageFromUrl(url:String){
    this.load(url)
}



