package com.example.foodflix.workers


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.repository.RecipeRepositorySingleton
import kotlinx.coroutines.coroutineScope

class RecipeFetchWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    object RequestType {
        const val GET_CANADIAN_RECIPES = "getCanadianRecipes"
        const val GET_TOP_RATED_RECIPES = "getTopRatedRecipes"
        const val INSERT_USER = "InsertUser"
    }

    private val recipeRepository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(appContext))

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val requestType = inputData.getString("requestType")
            val email = inputData.getString("email")

            // Call the API and get the recipes based on the request type
            when (requestType) {
                RequestType.GET_CANADIAN_RECIPES -> {
                    recipeRepository.getCanadianRecipes()
                }

                RequestType.INSERT_USER -> {
                    recipeRepository.insertUser(email!!)
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
