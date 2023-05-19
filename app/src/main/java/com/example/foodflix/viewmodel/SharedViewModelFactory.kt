package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.IllegalArgumentException

class SharedViewModelFactory(
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SharedViewModel::class.java)){
            return SharedViewModel(context) as T
        }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}