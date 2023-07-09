package com.example.tiviclon.views.detailShow

import androidx.lifecycle.*
import com.example.tiviclon.TiviClon
import com.example.tiviclon.data.database.entities.VODetailShow
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import kotlinx.coroutines.launch

class DetailShowViewModel(private val showId: String?, private val userId: String) : ViewModel() {
    private val detailShow: MutableLiveData<VODetailShow> by lazy {
        MutableLiveData<VODetailShow>().also {
            loadDetailShow()
        }
    }

    private lateinit var liveDataObserver: LiveData<VODetailShow>

    fun getDetailShow(): LiveData<VODetailShow> {
        return detailShow
    }

    private fun loadDetailShow() {
        showId?.let { showId ->
            liveDataObserver =
                TiviClon.appContainer.repository.getDetailShow(showID = showId, userId = userId)
            liveDataObserver.observeForever {
                detailShow.postValue(it)
            }
        }
    }

    fun updateFavState(userId: String, collectedShow: DetailShow) {
        viewModelScope.launch {
            TiviClon.appContainer.repository.updateFavUser(userId, collectedShow)
        }
    }

    fun requestDetailShow(showId: String): DetailShow {
        return TiviClon.appContainer.repository.getDetailShow(showId)
            .toDetailShow(false)
    }

    class Factory(private val showId: String?, private val userId: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailShowViewModel(showId, userId) as T
        }
    }

}