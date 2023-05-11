package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.ExternalIDResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieDetailViewModel (
    private val movieDatabaseDao: MovieDatabaseDao,
    application: Application,
    movie: Movie
) : AndroidViewModel(application){

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }

    init{
        setIsFavorite(movie)
        getExternalIDs(movie)
    }

    private val _externalIDs = MutableLiveData<ExternalIDResponse>()
    val externalIDs: LiveData<ExternalIDResponse>
        get() {
            return _externalIDs
        }

    fun getExternalIDs(movie: Movie){
        viewModelScope.launch {
            try{
                val externalIDResponse: ExternalIDResponse =
                    TMDBApi.movieListRetrofitService.getExternalIDs(movie_id = movie.id.toString())
                _externalIDs.value = externalIDResponse

                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }

    private fun setIsFavorite(movie: Movie){
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavourite(movie.id)
        }
    }

    fun onSaveMovieButtonClicked(movie: Movie){
        viewModelScope.launch {
            movieDatabaseDao.insert(movie)
            setIsFavorite(movie)
        }
    }

    fun onRemoveMovieButtonClicked(movie: Movie){
        viewModelScope.launch {
            movieDatabaseDao.delete(movie)
            setIsFavorite(movie)
        }
    }

}