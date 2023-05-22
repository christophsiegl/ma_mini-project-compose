package com.example.foodflix.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(
    context: Context,
) : ViewModel() {
    private val _repository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(context))

    private val _workManager = WorkManager.getInstance(context)
    private val _recipeList = _repository.recipes
    val recipeList: LiveData<List<Meal>>
        get() = _recipeList

    private var _favouriteRecipeIds = MutableStateFlow<String>("")
    val favouriteRecipeIds: StateFlow<String> = _favouriteRecipeIds.asStateFlow()

    //private val _workManager = WorkManager.getInstance(context)
    //private val _recipeList = repository.recipes
    //val recipeList: LiveData<List<Meal>>
    //    get() = _recipeList

    suspend fun getFavouriteRecipeIdsFromDatabase(email:String){
        var result = _repository.getFavouriteRecipes(email)
        _favouriteRecipeIds.value = result
    }
    fun createWorkManagerTaskMealDetail(requestString: String, mealID: String) {
        val inputData = Data.Builder()
            .putString("requestType", requestString)
            .putString("mealIDs", mealID)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<RecipeFetchWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        _workManager.enqueue(request)
    }

}