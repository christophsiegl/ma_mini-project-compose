package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kotlin.IllegalArgumentException

class ProfileScreenViewModelFactory(
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileScreenViewModel::class.java)){
            return ProfileScreenViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}