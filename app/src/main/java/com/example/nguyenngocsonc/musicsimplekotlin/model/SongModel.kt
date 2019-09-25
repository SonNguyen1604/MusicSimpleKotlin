package com.example.nguyenngocsonc.musicsimplekotlin.model

import android.database.Cursor
import android.provider.MediaStore
import java.util.*

/**
 * Created by nguyen.ngoc.sonc on 12/28/17.
 */
class SongModel(songName: String, songDuration: String, songPath: String) : Observable() {

    var mSongName: String = ""
        set(value) {
            field = value
            setChangedAndNotify("mSongName")
        }
    var mSongDuration: String = ""
        set(value) {
            field = value
            setChangedAndNotify("mSongDuration")
        }
    var mSongPath: String = ""
        set(value) {
            field = value
            setChangedAndNotify("mSongPath")
        }

    init {
        mSongName = songName
        mSongDuration = songDuration
        mSongPath = songPath
    }


    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }
}