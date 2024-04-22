package com.example.mymovielist.offlinedb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieModel: MovieModel)

    @Delete
    fun delete(movieModel: MovieModel)

    @Query("SELECT * from moviemodel ORDER BY id ASC")
    fun getAllMovie(): LiveData<List<MovieModel>>

    @Query("SELECT * FROM moviemodel WHERE judul = :title")
    fun getMovieByTitle(title: String): LiveData<MovieModel?>
}