package com.example.foodflix.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.database.RecipeDatabaseDao
import com.example.foodflix.model.Meal
import com.example.foodflix.network.DataFetchStatus
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val movieDatabaseDao: RecipeDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val recipeRepository = RecipeRepositorySingleton.getInstance(RecipeDatabase.getDatabase(application))
    val movieList = recipeRepository.recipes

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val workManager = WorkManager.getInstance(application)

    private val _navigateToMovieDetail = MutableLiveData<Meal?>()
    val navigateToMovieDetail: MutableLiveData<Meal?>
        get() {
            return _navigateToMovieDetail
        }

    init {
        if(recipeRepository.lastRequest == null) {
            createWorkManagerTask("getPopularMovies")
        }
        else {
            createWorkManagerTask(recipeRepository.lastRequest!!)
        }

        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getCanadianRecipes(){
        viewModelScope.launch {
            try{
                recipeRepository.getCanadianRecipes()
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }






/*
    fun onMovieListItemClicked(movie: Meal){
        _navigateToMovieDetail.value = movie
    }
    fun onMovieDetailNavigated(){
        _navigateToMovieDetail.value = null
    }
*/
    fun createWorkManagerTask(requestString: String){
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

        workManager.enqueue(request)
    }
}
