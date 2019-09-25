package com.example.nguyenngocsonc.musicsimplekotlin.data.local

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.example.nguyenngocsonc.musicsimplekotlin.data.DataSource
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel

/**
 * Created by nguyen.ngoc.sonc on 2/2/18.
 */
class SongLocalData(context: Context) : DataSource<SongModel>, AppCompatActivity() {

    private var mContentResolver: ContentResolver? = null
    private var mContext = context

    override fun getListModel(model: String?): List<SongModel> {
        var cursor = getCursor(model)
        var listSong = ArrayList<SongModel>()
        if(cursor!= null)
            while (cursor != null && cursor.moveToNext()) {
                var songName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                var songDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                var songPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                var song = SongModel(songName, songDuration, songPath)
                listSong.add(song)
            }
        return listSong
    }

    override fun getCursor(albumName: String?): Cursor {
        val where = android.provider.MediaStore.Audio.Media.ALBUM + "=?"
        val whereVal = arrayOf(albumName)
        mContentResolver = mContext.contentResolver
        return mContentResolver!!.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, whereVal, null)
    }
//
//    override fun getModel(id: Int): SongModel {
//
//    }
}