package com.example.foodflix.repository

import androidx.lifecycle.LiveData
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.model.UserData
import com.example.foodflix.network.RecipeApi
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RecipeRepository(private val database: RecipeDatabase) {
    val recipes: LiveData<List<Meal>> = database.recipeDatabaseDao().getAllRecipesAsLiveData()
    //val userData: LiveData<List<Meal>> = database.recipeDatabaseDao().getAllRecipesAsLiveData()

    private var _lastRequest: String? = null
    val lastRequest: String?
        get() = _lastRequest

    suspend fun getCanadianRecipes() {
        withContext(Dispatchers.IO) {
            val popularMeals = RecipeApi.recipeListRetrofitService.getCanadianMeals()
            database.recipeDatabaseDao().deleteAllRecipes() //delete after the meals are fetched!
            database.recipeDatabaseDao().insertAll(popularMeals.meals)
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