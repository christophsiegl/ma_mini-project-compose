package com.example.foodflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kotlin.IllegalArgumentException

class LoginScreenViewModelFactory(
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginScreenViewModel::class.java)){
            return LoginScreenViewModel() as T
        }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}