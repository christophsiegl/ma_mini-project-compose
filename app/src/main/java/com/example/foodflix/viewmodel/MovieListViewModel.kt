package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabase
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.repository.MovieRepositorySingleton
import com.ltu.m7019e.v23.themoviedb.workers.MovieFetchWorker
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val moviesRepository = MovieRepositorySingleton.getInstance(MovieDatabase.getDatabase(application))
    val movieList = moviesRepository.movies

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val workManager = WorkManager.getInstance(application)

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() {
            return _navigateToMovieDetail
        }

    init {
        if(moviesRepository.lastRequest == null) {
            createWorkManagerTask("getPopularMovies")
        }
        else {
            createWorkManagerTask(moviesRepository.lastRequest!!)
        }

        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            try{
                moviesRepository.getPopularMovies()
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            try{
                moviesRepository.getTopRatedMovies()
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                //_movieList.value = arrayListOf()
            }
        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            //_movieList.value = movieDatabaseDao.getAllMovies()
        }
    }

    fun addMovie(){
        viewModelScope.launch {
            //_movieList.value?.let { movieDatabaseDao.insert(it[0]) }
        }
    }

    fun onMovieListItemClicked(movie: Movie){
        _navigateToMovieDetail.value = movie
    }
    fun onMovieDetailNavigated(){
        _navigateToMovieDetail.value = null
    }

    fun createWorkManagerTask(requestString: String){
        val inputData = Data.Builder()
            .putString("requestType", requestString)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<MovieFetchWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(request)
    }
}
