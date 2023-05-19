package com.example.foodflix.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
    context: Context,
) : ViewModel(){
    private val _workManager = WorkManager.getInstance(context)
    private val _canSearch = MutableStateFlow(false)
    val canSearch = _canSearch.asStateFlow()

    private val _searchFieldVisible = MutableStateFlow(false)
    val searchFieldVisible = _searchFieldVisible.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _userImageUrl = MutableStateFlow<String?>(null)
    val userImageUrl: StateFlow<String?> = _userImageUrl.asStateFlow()

    fun setUserImageUrl(imageUrl: String?) {
        _userImageUrl.value = imageUrl
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    fun setCanSearch(value: Boolean) {
        _canSearch.value = value
    }


    fun setSearchFieldVisible(value: Boolean) {
        _searchFieldVisible.value = value
    }

    fun createWorkManagerTask(requestString: String) {
        val inputData = Data.Builder()
            .putString("requestType", requestString)
            .putString("mealName", _searchQuery.value)
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
