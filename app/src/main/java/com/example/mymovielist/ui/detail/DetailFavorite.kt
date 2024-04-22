package com.example.mymovielist.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovielist.databinding.DetailFavoriteBinding
import com.example.mymovielist.offlinedb.MovieModel
import com.example.mymovielist.offlinedb.repository.MovieRepo

class DetailFavorite : AppCompatActivity() {
    private var _binding: DetailFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie = intent.getParcelableExtra<MovieModel>("movie")

        movie?.let { bind(it) }

        binding.fabFavDel.setOnClickListener {
            movie?.let { delete(it) }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun bind(movie: MovieModel) {
        Glide.with(binding.root)
            .load(movie.poster)
            .into(binding.tvPosterMovie)

        binding.tvNamaMovie.text = movie.judul
        binding.tvGenreMovie.text = movie.genre
        binding.tvRatingMovie.text = movie.rating.toString()
        binding.tvTahunRelease.text = movie.tahunRilis.toString()
        binding.tvDesc.text = movie.desc
        binding.tvDirectorMovie.text = movie.director
    }

    private fun delete(movie: MovieModel) {
        val movieRepo = MovieRepo(application)
        movieRepo.delete(movie)
        Toast.makeText(this, "${movie.judul} has been removed from favorites", Toast.LENGTH_SHORT).show()
        finish()
    }
}