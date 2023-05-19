package com.example.foodflix.viewmodel

import android.content.Context
import android.service.autofill.UserData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.repository.RecipeRepository
import com.example.foodflix.repository.RecipeRepositorySingleton

class ProfileScreenViewModel(
    context: Context,
    repository: RecipeRepository = RecipeRepositorySingleton.getInstance(
        RecipeDatabase.getDatabase(
            context
        )
    )
) : ViewModel() {
    private val _recipeList = repository
    suspend fun readAgeFromDatabase(email:String): Int {
        return _recipeList.getAgeFromUser(email)
    }

    suspend fun getAllUsers(): List<com.example.foodflix.model.UserData> {
        return _recipeList.getAllUsers()
    }
    suspend fun updateAgeInDatabase(age: Int, email: String) {
        _recipeList.updateUserAge(email,age)
    }
}