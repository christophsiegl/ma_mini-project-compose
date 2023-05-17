package com.example.foodflix.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkManager
import com.example.foodflix.database.Recipes
import com.example.foodflix.network.DataFetchStatus
import com.example.foodflix.network.RecipeLookupResponse

class RecipeDetailViewModel(
    private val context: Context,
    private val RecipeDatabaseDao: Context,
    application: Application,
    recipe: Recipes
) : AndroidViewModel(application){

    // Did we even use this in our labs?
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
        TODO("How should this viewmodel be initialized?")
    }

    private val _mealDetails = MutableLiveData<RecipeLookupResponse>()
    val mealDetails: LiveData<RecipeLookupResponse>
        get() {
            return _mealDetails
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