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
        const val GET_RECIPE_DETAIL = "setRecipeDetail"
        const val SET_FAVOURITE_RECIPE = "setFavouriteRecipe"
    }

    private val recipeRepository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(appContext))

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val requestType = inputData.getString("requestType")

            // Call the API and get the recipes based on the request type
            when (requestType) {
                RequestType.GET_CANADIAN_RECIPES -> {
                    recipeRepository.getCanadianRecipes()
                }
                RequestType.GET_RECIPE_DETAIL -> {
                    val mealID = inputData.getString("mealID")
                    recipeRepository.getRecipeDetails(mealID!!)
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
