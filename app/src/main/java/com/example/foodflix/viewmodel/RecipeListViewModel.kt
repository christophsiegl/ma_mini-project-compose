package com.example.foodflix.viewmodel

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.model.Meal
import com.example.foodflix.network.DataFetchStatus
import com.example.foodflix.repository.RecipeRepository
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.workers.RecipeFetchWorker

class RecipeListViewModel(
    context: Context,
    repository: RecipeRepository = RecipeRepositorySingleton.getInstance(
        RecipeDatabase.getDatabase(
            context
        )
    )
) : ViewModel() {
    private val _workManager = WorkManager.getInstance(context)
    private val _recipeList = repository.recipes
    val recipeList: LiveData<List<Meal>>
        get() = _recipeList

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _navigateToMovieDetail = MutableLiveData<Meal?>()
    val navigateToMovieDetail: MutableLiveData<Meal?>
        get() {
            return _navigateToMovieDetail
        }

    init {
        if (repository.lastRequest == null) {
            createWorkManagerTask(RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES)
        } else {
            createWorkManagerTask(repository.lastRequest!!)
        }

        _dataFetchStatus.value = DataFetchStatus.LOADING
    }
    /*
        fun onMovieListItemClicked(movie: Meal){
            _navigateToMovieDetail.value = movie
        }
        fun onMovieDetailNavigated(){
            _navigateToMovieDetail.value = null
        }
    */
    fun createWorkManagerTask(requestString: String) {
        val inputData = Data.Builder()
            .putString("requestType", requestString)
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
