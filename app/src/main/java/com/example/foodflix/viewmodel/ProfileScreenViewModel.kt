package com.example.foodflix.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileScreenViewModel : ViewModel() {

    fun onUpdateClick(age:Int,email:String) {
            updateAgeInDatabase(age,email)
    }

    fun readAgeFromDatabase(email:String): Int {
        // TODO: Real implementation
        return 0
    }

    fun updateAgeInDatabase(age: Int, email: String) {
        // TODO: Real implementation
    }
}