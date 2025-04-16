package com.xsat.androidexpertdicoding.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase

class DetailViewModel(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> = _favoriteState

    fun getDetailUser(username: String) =
        githubUserUseCase.getDetailUser(username).asLiveData()

    suspend fun toggleFavorite(user: GithubUsers) {
        val newState = !(_favoriteState.value ?: user.isFavorite)
        githubUserUseCase.setFavoriteUser(user, newState)
        _favoriteState.postValue(newState)
    }

    fun setInitialFavoriteState(isFavorite: Boolean) {
        _favoriteState.value = isFavorite
    }
}

