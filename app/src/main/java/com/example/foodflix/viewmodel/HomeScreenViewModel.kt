package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.repository.RecipeRepositorySingleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(
    context: Context,
) : ViewModel() {
    private val _repository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(context))

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
}