package com.example.mymovielist.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mymovielist.offlinedb.MovieModel
import com.example.mymovielist.offlinedb.repository.MovieRepo

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepo = MovieRepo(application)
    val allMovies: LiveData<List<MovieModel>>

    init {
        allMovies = repository.getAllMovie()
    }
}