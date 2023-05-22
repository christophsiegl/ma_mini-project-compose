package com.example.foodflix.repository

import androidx.lifecycle.LiveData
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.model.UserData
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
            val popularMeals = RecipeApi.recipeListRetrofitService.getCanadianMeals()
            database.recipeDatabaseDao().deleteAllRecipes()
            database.recipeDatabaseDao().insertAll(popularMeals.meals)
        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES
    }

    suspend fun getRecipesFromSearch(mealName : String) {
        withContext(Dispatchers.IO) {
            val mealsFromSearch = RecipeApi.recipeListRetrofitService.getMealBySearch(mealName)
            database.recipeDatabaseDao().deleteAllRecipes()
            database.recipeDatabaseDao().insertAll(mealsFromSearch.meals)
        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES
    }


    suspend fun updateUserAge(email:String,age:Int) {
        withContext(Dispatchers.IO) {
            database.recipeDatabaseDao().update(email,age) //update Table

        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES
    }

    suspend fun insertUser(email:String) {
        withContext(Dispatchers.IO) {
            database.recipeDatabaseDao().insert(UserData(email,0)) //Insert new User

        }
        _lastRequest = RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES
    }

    suspend fun getAgeFromUser(email: String): Int {
        return database.recipeDatabaseDao().getAge(email)
    }

    suspend fun getAllUsers(): List<UserData> {
        return database.recipeDatabaseDao().getAllUsers()
    }

    suspend fun getRecipeDetails(id: String) {
        withContext(Dispatchers.IO) {
            val mealDetail = RecipeApi.recipeListRetrofitService.getMealById(id)
            database.recipeDatabaseDao().deleteAllRecipeDetails()
            database.recipeDatabaseDao().insert(mealDetail.meals[0]) //always only one element!
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