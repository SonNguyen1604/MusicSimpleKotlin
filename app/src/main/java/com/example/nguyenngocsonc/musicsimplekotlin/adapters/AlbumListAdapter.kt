package com.example.nguyenngocsonc.musicsimplekotlin.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nguyenngocsonc.musicsimplekotlin.Interface.CustomAlbumItemClickListener
import com.example.nguyenngocsonc.musicsimplekotlin.R
import com.example.nguyenngocsonc.musicsimplekotlin.model.AlbumModel
import java.util.concurrent.TimeUnit

/**
 * Created by nguyen.ngoc.sonc on 1/8/18.
 */
class AlbumListAdapter(AlbumModel: ArrayList<AlbumModel>, context: Context, onClick: CustomAlbumItemClickListener) : RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>() {
    private var mAlbumModel = AlbumModel
    private var mOnClick = onClick

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumListViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.album_row, parent, false)
        return AlbumListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder?, position: Int) {
        var model = mAlbumModel[position]
        var albumName = model.mAlbumName
        var albumArt = model.mAlbumArt

        holder!!.albumTV.text = albumName
        holder.albumArt.setImageBitmap(BitmapFactory.decodeFile(albumArt))
        holder.setOnCustomAlbumItemClickListener(mOnClick)
    }

    fun toMands(millis: Long): String {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }

    override fun getItemCount(): Int {
        return mAlbumModel.size
    }

    class AlbumListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var albumTV: TextView
        var albumArt: ImageView
        private var mCustomItemClickListener: CustomAlbumItemClickListener? = null

        init {
            albumTV = itemView.findViewById(R.id.album_name_tv)
            albumArt = itemView.findViewById(R.id.al_img_view)
            itemView.setOnClickListener(this)
        }

        fun setOnCustomAlbumItemClickListener(customItemClickListener: CustomAlbumItemClickListener) {
            this.mCustomItemClickListener = customItemClickListener
        }

        override fun onClick(v: View?) {
            this.mCustomItemClickListener!!.onCustomAlbumItemClick(v!!, adapterPosition)
        }
    }
}