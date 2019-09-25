package com.example.nguyenngocsonc.musicsimplekotlin.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.nguyenngocsonc.musicsimplekotlin.databinding.MusicRowBinding
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel
import com.example.nguyenngocsonc.musicsimplekotlin.viewmodels.AlbumViewModel
import com.example.nguyenngocsonc.musicsimplekotlin.viewmodels.SongViewModel
import java.util.concurrent.TimeUnit

/**
 * Created by nguyen.ngoc.sonc on 12/28/17.
 */
class SongListAdapter(SongModel: ArrayList<SongModel>, context: Context, viewModel: AlbumViewModel) : RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    private var mSongModel = SongModel
    private var mViewModel: AlbumViewModel = viewModel

    companion object {
        val MUSICLIST = ""
        val MUSICTEMPOS = "pos"
    }

    private var mLayoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongListViewHolder {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent!!.context)
        var binding: MusicRowBinding = MusicRowBinding.inflate(mLayoutInflater!!, parent, false)
        return SongListAdapter.SongListViewHolder(binding, mViewModel)
    }

    override fun onBindViewHolder(holder: SongListViewHolder?, position: Int) {
        holder!!.bind(mSongModel.get(position))
    }

    fun updateItem(itemList: List<SongModel>) {
        mSongModel.clear()
        for(i in itemList) {
            var songDu = toMands(i.mSongDuration.toLong())
            i.mSongDuration = songDu
        }
        mSongModel!!.addAll(itemList)
        notifyDataSetChanged()
    }

    fun toMands(millis: Long): String {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }

    override fun getItemCount(): Int = mSongModel.size

    class SongListViewHolder(binding: MusicRowBinding, viewModel: AlbumViewModel) : RecyclerView.ViewHolder(binding.root) {

        private var mBinding: MusicRowBinding = binding
        private var mViewModel = viewModel

        fun bind(item: SongModel) {
            mBinding.item = item
            mBinding.viewModel = mViewModel
            mBinding.executePendingBindings()
        }
    }
}