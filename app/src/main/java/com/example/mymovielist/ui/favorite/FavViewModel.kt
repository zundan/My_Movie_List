package com.example.mymovielist.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovielist.offlinedb.MovieModel
import com.example.mymovielist.offlinedb.repository.MovieRepo

class FavViewModel(application: Application) : ViewModel() {
    private val mMovieRepo: MovieRepo = MovieRepo(application)

    fun getAllMember(): LiveData<List<MovieModel>> = mMovieRepo.getAllMovie()
}