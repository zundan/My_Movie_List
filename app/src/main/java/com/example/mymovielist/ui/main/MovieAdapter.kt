package com.example.mymovielist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovielist.R
import com.example.mymovielist.databinding.ItemMovieBinding
import com.example.mymovielist.model.Movie

class MovieAdapter(context: MainActivity, private var listMovie: ArrayList<Movie>?) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val listener: FireBaseDataListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie?.size!!

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie?.get(position))
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)

        fun bind(movie: Movie?) {
            movie?.let {
                Glide.with(itemView)
                    .load(movie.poster)
                    .into(binding.tvPosterMovie)
                binding.tvTahunRelease.text = movie.tahunRilis.toString()
                binding.tvNamaMovie.text = movie.judul
                binding.tvGenreMovie.text = movie.genre
                binding.tvRatingMovie.text = movie.rating.toString()

                binding.cvMovie.setOnClickListener {
                    listener.onDataClick(movie)
                }
            }
        }
    }

    fun setSearchedList(listMovie: ArrayList<Movie>) {
        this.listMovie = listMovie
        notifyDataSetChanged()
    }

    interface FireBaseDataListener {
        fun onDataClick(movie: Movie??)
    }

    init {
        listener = context as FireBaseDataListener
    }
}