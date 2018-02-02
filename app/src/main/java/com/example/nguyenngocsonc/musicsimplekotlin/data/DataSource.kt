package com.example.nguyenngocsonc.musicsimplekotlin.data

import android.content.Context
import android.database.Cursor

/**
 * Created by nguyen.ngoc.sonc on 2/2/18.
 */
interface DataSource<T> {
//    fun getModel(id: Int): T
    fun getListModel(model: String?): List<T>
    fun getCursor(model: String?): Cursor
}