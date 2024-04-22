package com.example.mymovielist.offlinedb.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mymovielist.offlinedb.MovieDao
import com.example.mymovielist.offlinedb.MovieModel
import com.example.mymovielist.offlinedb.MovieRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepo(application: Application) {
    private val mMovieDao: MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val allMovies: LiveData<List<MovieModel>>

    init {
        val db = MovieRoomDB.getDatabase(application)
        mMovieDao = db.movieDao()
        allMovies = mMovieDao.getAllMovie()
    }

    fun getAllMovie(): LiveData<List<MovieModel>> = mMovieDao.getAllMovie()

    fun insert(movie: MovieModel) {
        executorService.execute { mMovieDao.insert(movie) }
    }

    fun delete(movie: MovieModel) {
        executorService.execute { mMovieDao.delete(movie) }
    }
}