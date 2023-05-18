package com.example.foodflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kotlin.IllegalArgumentException

class ProfileScreenViewModelFactory(
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileScreenViewModel::class.java)){
            return ProfileScreenViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}