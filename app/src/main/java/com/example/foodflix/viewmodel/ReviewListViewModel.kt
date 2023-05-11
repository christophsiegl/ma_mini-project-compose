package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.ReviewAuthorDetails
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieResponse
import com.ltu.m7019e.v23.themoviedb.network.ReviewResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch
import java.util.Dictionary

class ReviewListViewModel (
    application: Application,
    movie_id: String
) : AndroidViewModel(application){



    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() {
            return _reviewList
        }

    private val _navigateToReviewList = MutableLiveData<Review?>()
    val navigateToReviewList: MutableLiveData<Review?>
        get() {
            return _navigateToReviewList
        }


    init {
        getReviews(movie_id)
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }
    fun getReviews(movie_id : String){
        viewModelScope.launch {
            try{
                val reviewResponse: ReviewResponse =
                TMDBApi.movieListRetrofitService.getReviews(movieId = movie_id)
                _reviewList.value = reviewResponse.results.map {
                    Review(
                        author = it.author,
                        content = it.content,
                        created_at = it.created_at,
                        author_details = it.author_details?.let {
                            ReviewAuthorDetails(
                                name = it.name,
                                username = it.username,
                                avatar_path = it.avatar_path,
                                rating = it.rating.toString()
                            )
                        }
                    )
                }

                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception){
                println(e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _reviewList.value = arrayListOf()
            }
        }
    }

}