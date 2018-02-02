package com.example.nguyenngocsonc.musicsimplekotlin.viewmodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.nguyenngocsonc.musicsimplekotlin.BR
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel
import java.util.*

/**
 * Created by nguyen.ngoc.sonc on 2/1/18.
 */
class SongViewModel(private val song: SongModel) : Observer, BaseObservable() {

    init {
        song.addObserver(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is String) {
            if (arg == "mSongName") {
                notifyPropertyChanged(BR.songName)
            } else if (arg == "mSongDuration") {
                notifyPropertyChanged(BR.songDuration)
            } else if (arg == "mSongPath") {
                notifyPropertyChanged(BR.songPath)
            }
        }
    }

    val songName: String
        @Bindable get() {
            return song.mSongName
        }

    val songDuration: String
        @Bindable get() {
            return song.mSongDuration
        }
    val songPath: String
        @Bindable get() {
            return song.mSongPath
        }
}