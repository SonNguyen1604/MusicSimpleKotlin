package com.example.nguyenngocsonc.musicsimplekotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.nguyenngocsonc.musicsimplekotlin.databinding.AlbumRowBinding
import com.example.nguyenngocsonc.musicsimplekotlin.model.AlbumModel
import com.example.nguyenngocsonc.musicsimplekotlin.viewmodels.AlbumViewModel

/**
 * Created by nguyen.ngoc.sonc on 1/8/18.
 */
class AlbumListAdapter(AlbumModel: ArrayList<AlbumModel>, context: Context, viewModel: AlbumViewModel) : RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {
    private var mAlbumModel: ArrayList<AlbumModel> = AlbumModel
    private var mViewModel: AlbumViewModel = viewModel

    private var mLayoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumListAdapter.ViewHolder {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent!!.context)
        var binding: AlbumRowBinding  = AlbumRowBinding.inflate(mLayoutInflater!!, parent, false)
        return AlbumListAdapter.ViewHolder(binding, mViewModel)
    }

    override fun onBindViewHolder(holder: AlbumListAdapter.ViewHolder?, position: Int) {
        holder!!.bind(mAlbumModel.get(position))
    }

    override fun getItemCount(): Int = mAlbumModel.size

    class ViewHolder(binding: AlbumRowBinding, viewModel: AlbumViewModel) : RecyclerView.ViewHolder(binding.root){
        private var mBinding: AlbumRowBinding = binding
        private var mViewModel = viewModel

        fun bind(item: AlbumModel) {
            mBinding.item = item
            mBinding.position = adapterPosition
            mBinding.viewModel = mViewModel
            mBinding.executePendingBindings()
        }
    }
}