package com.example.foodflix.repository

import androidx.lifecycle.LiveData
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail
import com.example.foodflix.network.RecipeApi
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RecipeRepository(private val database: RecipeDatabase) {
    val recipes: LiveData<List<Meal>> = database.recipeDatabaseDao().getAllRecipesAsLiveData()
    val recipeDetail: LiveData<List<MealDetail>> = database.recipeDatabaseDao().getAllRecipeDetailsAsLiveData()

    private var _lastRequest: String? = null
    val lastRequest: String?
        get() = _lastRequest

    suspend fun getCanadianRecipes() {
        withContext(Dispatchers.IO) {
            database.recipeDatabaseDao().deleteAllRecipes() //delete after the meals are fetched!
            val popularMeals = RecipeApi.recipeListRetrofitService.getCanadianMeals()
            //val mealDetail = RecipeApi.recipeListRetrofitService.getMealById()
            //val mealDetail = RecipeApi.recipeListRetrofitService.getMealByA()
            database.recipeDatabaseDao().insertAll(popularMeals.meals)
            //database.recipeDatabaseDao().insertAllMealDetails(mealDetail.meals)
        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES
    }

    suspend fun getRecipeDetails(id: String) {
        withContext(Dispatchers.IO) {
            database.recipeDatabaseDao().deleteAllRecipeDetails()
            val mealDetail = RecipeApi.recipeListRetrofitService.getMealById("52772")
            //val mealDetail = RecipeApi.recipeListRetrofitService.getMealByA()
            database.recipeDatabaseDao().insert(mealDetail.meals[0])
        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL
    }
}

object RecipeRepositorySingleton {
    private lateinit var instance: RecipeRepository
    fun getInstance(database: RecipeDatabase): RecipeRepository {
        synchronized(RecipeRepositorySingleton::class.java) {
            if (!::instance.isInitialized) {
                instance = RecipeRepository(database)
            }
        }
        return instance
    }
}