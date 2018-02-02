package com.example.nguyenngocsonc.musicsimplekotlin.utils

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.bumptech.glide.Glide
import com.example.nguyenngocsonc.musicsimplekotlin.builder.BlurBuilder
import java.io.File

/**
 * Created by nguyen.ngoc.sonc on 2/8/18.
 */

@BindingAdapter("adapter", "isAlbum")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?, isAlbum: Boolean) {
    if (adapter == null) return
    view.adapter = adapter

    if(isAlbum){
        val layoutManager: CarouselLayoutManager = object : CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true){}
        layoutManager.setPostLayoutListener(object : CarouselZoomPostLayoutListener() {})
        view.addOnScrollListener(object : CenterScrollListener(){})
        view.setHasFixedSize(true)
        view.layoutManager = layoutManager
    }
}

@BindingAdapter("albumArt")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}