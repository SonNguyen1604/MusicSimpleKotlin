package com.example.nguyenngocsonc.musicsimplekotlin.model

import android.database.Cursor
import android.provider.MediaStore
import java.util.*

/**
 * Created by nguyen.ngoc.sonc on 1/8/18.
 */
class AlbumModel(albumName: String, albumArt: String) : Observable() {

    var mAlbumName: String = ""
    set(value) {
        field = value
        setChangedAndNotify("mAlbumName")
    }
    var mAlbumArt: String = ""
    set(value) {
        field = value
        setChangedAndNotify("mAlbumArt")
    }

    init {
        mAlbumName = albumName
        mAlbumArt = albumArt
    }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }
}