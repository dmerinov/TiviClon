package com.example.tiviclon.views.detailShow.viewmodel

import androidx.lifecycle.*
import com.example.tiviclon.TiviClon
import com.example.tiviclon.data.database.entities.VODetailShow
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import kotlinx.coroutines.launch

class DetailShowFragmentViewModel(private val showId: String?, private val userId: String) : ViewModel() {
    private lateinit var detailShow: LiveData<VODetailShow>

    init {
        showId?.let {
            detailShow = TiviClon.appContainer.repository.getDetailShow(it, userId)
        } ?: run {
            detailShow = MutableLiveData<VODetailShow>()
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

    fun getDetailShow() = detailShow

    class Factory(private val showId: String?, private val userId: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailShowFragmentViewModel(showId, userId) as T
        }
    }
}