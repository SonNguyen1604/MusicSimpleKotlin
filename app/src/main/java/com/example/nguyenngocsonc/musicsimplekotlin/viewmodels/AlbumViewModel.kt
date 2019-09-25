package com.example.nguyenngocsonc.musicsimplekotlin.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.nguyenngocsonc.musicsimplekotlin.BR
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.AlbumListAdapter
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter
import com.example.nguyenngocsonc.musicsimplekotlin.builder.BlurBuilder
import com.example.nguyenngocsonc.musicsimplekotlin.data.local.AlbumLocalData
import com.example.nguyenngocsonc.musicsimplekotlin.data.local.SongLocalData
import com.example.nguyenngocsonc.musicsimplekotlin.model.AlbumModel
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by nguyen.ngoc.sonc on 2/1/18.
 */
class AlbumViewModel (albumData: AlbumLocalData, context: Context) : Observer, BaseObservable() {

    private var listData: ArrayList<AlbumModel> = ArrayList()
    private var listSongInAlbum: ArrayList<SongModel> = ArrayList()
    private var mContext: Context = context

    override fun update(o: Observable?, arg: Any?) {
        if(arg is String) { }
    }

    @get:Bindable
    var albumArt: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.albumArt)
    }

    var adapter: AlbumListAdapter

    var songAdapter: SongListAdapter

    init {
        listData = ArrayList(albumData.getListModel(null))
        adapter = AlbumListAdapter(listData, mContext, this)
        songAdapter = SongListAdapter(listSongInAlbum, mContext, this)
        albumArt = listData!!.first().mAlbumArt
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun onItemClick(pos: Int) {
        var model = listData[pos]
        albumArt = model.mAlbumArt
        if (model.mAlbumArt != null) {
            val bmImg = BitmapFactory.decodeFile(model.mAlbumArt)
            var blurBuilder = BlurBuilder()
            var imageBlur = blurBuilder.blur(mContext, bmImg)
            val background = BitmapDrawable(imageBlur)
//            background_app.setImageBitmap(imageBlur)
        } else {
//            background_app.setBackgroundColor(Color.parseColor("#3F51B5"))
        }
        getSongInAlbum(model)
        songAdapter!!.updateItem(listSongInAlbum)
    }

    private fun getSongInAlbum(album: AlbumModel) {
        var songLocalData = SongLocalData(mContext)
        listSongInAlbum = ArrayList(songLocalData.getListModel(album.mAlbumName))
    }
}