package com.example.nguyenngocsonc.musicsimplekotlin.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nguyenngocsonc.musicsimplekotlin.Interface.CustomItemClickListener
import com.example.nguyenngocsonc.musicsimplekotlin.R
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel
import java.util.concurrent.TimeUnit

/**
 * Created by nguyen.ngoc.sonc on 12/28/17.
 */
class SongListAdapter(SongModel: ArrayList<SongModel>, context: Context, onClick: CustomItemClickListener) : RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {
    private var mSongModel = SongModel
    private var mOnClick = onClick

    companion object {
        val MUSICLIST = ""
        val MUSICTEMPOS = "pos"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongListViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.music_row, parent, false)
        return SongListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongListViewHolder?, position: Int) {
        var model = mSongModel[position]
        var songName = model.mSongName
        var songDuration = toMands(model.mSongDuration.toLong())
//        var songCover = model.mSongCover

        holder!!.songTV.text = songName
        holder.durationTV.text = songDuration
//        holder.albumArt.setImageBitmap(BitmapFactory.decodeFile(songCover))
        holder.setOnCustomItemClickListener(mOnClick)
    }

    fun toMands(millis: Long): String {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }

    override fun getItemCount(): Int {
        return mSongModel.size
    }

    class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var songTV: TextView
        var durationTV: TextView
//        var albumArt: ImageView
        var mCustomItemClickListener: CustomItemClickListener? = null

        init {
            songTV = itemView.findViewById(R.id.song_name_tv)
            durationTV = itemView.findViewById(R.id.song_duration_tv)
//            albumArt = itemView.findViewById(R.id.al_img_view)
            itemView.setOnClickListener(this)
        }

        fun setOnCustomItemClickListener(customItemClickListener: CustomItemClickListener) {
            this.mCustomItemClickListener = customItemClickListener
        }

        override fun onClick(v: View?) {
            this.mCustomItemClickListener!!.onCustomItemClick(v!!, adapterPosition)
        }
    }
}

//object:CustomItemClickListener {
//    override fun onCustomItemClick(view: View, pos: Int) {
//        for (i in 0 until mSongModel.size) {
//            allMusicList.add(mSongModel[i].mSongPath)
//        }
//        var musicDataIntent = Intent(mContext, PlayMusicService::class.java)
//        musicDataIntent.putStringArrayListExtra(MUSICLIST, allMusicList)
//        musicDataIntent.putExtra(MUSICTEMPOS, pos)
//        mContext.startService(musicDataIntent)
//
//    }
//}