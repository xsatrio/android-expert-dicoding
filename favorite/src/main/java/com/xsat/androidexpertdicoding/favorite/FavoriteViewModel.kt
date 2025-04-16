package com.xsat.androidexpertdicoding.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase

class FavouriteViewModel(movieUseCase: GithubUserUseCase): ViewModel() {
    val favUsers = movieUseCase.getFavoriteUsers().asLiveData()
}