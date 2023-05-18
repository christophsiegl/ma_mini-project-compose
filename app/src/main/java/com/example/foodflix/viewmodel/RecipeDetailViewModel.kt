package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.database.Recipes
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail
import com.example.foodflix.network.DataFetchStatus
import com.example.foodflix.repository.RecipeRepository
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.workers.RecipeFetchWorker

class RecipeDetailViewModel(
    context: Context,
    repository: RecipeRepository = RecipeRepositorySingleton.getInstance(
        RecipeDatabase.getDatabase(
            context
        )
    )
) : ViewModel(){

    private val _workManager = WorkManager.getInstance(context)
    private val _recipeList = repository.recipes
    val recipeList: LiveData<List<Meal>>
        get() = _recipeList

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    // This still needs to be implented in a good way.
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }

    init{
        if (repository.lastRequest == null) {
            //createWorkManagerTask(RecipeFetchWorker.RequestType.SET_RECIPE_DETAIL)
        } else {
            // TODO("fix this")
            //createWorkManagerTask(repository.lastRequest!!, "")
        }

        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    private val _mealDetails = repository.recipeDetail
    val mealDetails: LiveData<List<MealDetail>>
        get() {
            return _mealDetails
        }
    fun createWorkManagerTaskMealDetail(requestString: String, mealID: String) {
        val inputData = Data.Builder()
            .putString("requestType", requestString)
            .putString("mealID", mealID)
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

    private fun setIsFavorite(recipe: Recipes){
        TODO("Fix favorite stuff")
    }

    fun onSaveMovieButtonClicked(recipe: Recipes){
        TODO("Fix the save button")
    }

    fun onRemoveMovieButtonClicked(recipe: Recipes){
        TODO("Fix the remove from favorites button")
    }

}