package com.example.mymovielist.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovielist.offlinedb.MovieModel
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.mymovielist.databinding.ItemMovieBinding
import com.example.mymovielist.ui.detail.DetailFavorite

class FavMoviesAdapter : RecyclerView.Adapter<FavMoviesAdapter.ListViewHolder>() {
    private lateinit var binding: ItemMovieBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.poster)
                    .into(tvPosterMovie)
                tvTahunRelease.text = movie.tahunRilis.toString()
                tvNamaMovie.text = movie.judul
                tvGenreMovie.text = movie.genre
                tvRatingMovie.text = movie.rating.toString()
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailFavorite::class.java)
                intent.putExtra("movie", movie)
                itemView.context.startActivity(intent)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }
}