package com.ida.mymovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ida.mymovie.databinding.ListMovieBinding
import com.ida.mymovie.model.ResultX

class TopRatedAdapter(private val onItemClick: OnClickListener):
    RecyclerView.Adapter<TopRatedAdapter.ViewHolder>(){

    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"


    private val diffCallBack = object : DiffUtil.ItemCallback<ResultX>(){
        override fun areItemsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListMovieBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TopRatedAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ListMovieBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultX) {
            binding.apply {
                Glide.with(binding.root).load(IMAGE_BASE+data.posterPath).into(ivImage)
                tvTittle.text = data.originalTitle
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: ResultX)
    }

}