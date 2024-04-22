package com.example.mymovielist.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovielist.ui.detail.MovieViewModel
import com.example.mymovielist.ui.favorite.FavViewModel

class FavViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavViewModelFactory::class.java) {
                    INSTANCE = FavViewModelFactory(application)
                }
            }
            return INSTANCE as FavViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}