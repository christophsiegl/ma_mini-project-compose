package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.Review

class ReviewListViewModelFactory (
                                  private val application: Application,
                                  private val movie_id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReviewListViewModel::class.java)){
            return ReviewListViewModel(application,movie_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}