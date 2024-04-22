package com.example.mymovielist.offlinedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieModel::class], version = 1)
abstract class MovieRoomDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDB? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieRoomDB {
            if (INSTANCE == null) {
                synchronized(MovieRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieRoomDB::class.java,
                        "movie_database"
                    ).build()
                }
            }
            return INSTANCE as MovieRoomDB
        }
    }
}
