package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.IllegalArgumentException

class RecipeDetailViewModelFactory(
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipeDetailViewModel::class.java)){
            return RecipeDetailViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}