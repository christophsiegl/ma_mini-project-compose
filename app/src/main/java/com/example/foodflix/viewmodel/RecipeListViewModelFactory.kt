package com.example.foodflix.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodflix.database.RecipeDatabaseDao
import kotlin.IllegalArgumentException

class RecipeListViewModelFactory(
    private val movieDatabaseDao: RecipeDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipeListViewModel::class.java)){
            return RecipeListViewModel(movieDatabaseDao, application) as T
        }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}