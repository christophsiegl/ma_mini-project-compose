package com.example.foodflix.workers


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.repository.RecipeRepositorySingleton
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RecipeFetchWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val recipeRepository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(appContext))

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val requestType = inputData.getString("requestType")

            // Call the API and get the recipes based on the request type
            when (requestType) {
                "getPopularRecipes" -> {
                    recipeRepository.getCanadianRecipes()
                }
                else -> throw IllegalArgumentException("Invalid request type")
            }
            Result.success()

        } catch (e: Exception) {
            // If an error occurred, return failure
            Result.failure()
        }
    }
}
