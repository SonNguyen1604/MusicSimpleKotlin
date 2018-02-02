package com.example.nguyenngocsonc.musicsimplekotlin.data.local

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.example.nguyenngocsonc.musicsimplekotlin.data.DataSource
import com.example.nguyenngocsonc.musicsimplekotlin.model.AlbumModel
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel

/**
 * Created by nguyen.ngoc.sonc on 2/8/18.
 */
class AlbumLocalData(context: Context): DataSource<AlbumModel>, AppCompatActivity() {

    private var mContentResolver: ContentResolver? = null
    private var mContext = context

    override fun getListModel(model: String?): List<AlbumModel> {
        var cursor = getCursor(model)
        var listAlbum = ArrayList<AlbumModel>()

        while (cursor != null && cursor.moveToNext()) {
            var albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
            var albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))

            if(listAlbum.none { x -> x.mAlbumName == albumName }) {
                listAlbum.add(AlbumModel(albumName, albumArt))
            }
        }
        return listAlbum
    }

    override fun getCursor(model: String?): Cursor {
        mContentResolver = mContext.contentResolver
        return mContentResolver!!.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null)
    }
}