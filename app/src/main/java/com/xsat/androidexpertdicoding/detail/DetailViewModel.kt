package com.xsat.androidexpertdicoding.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase

class DetailViewModel(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {

    fun getDetailUser(username: String) =
        githubUserUseCase.getDetailUser(username).asLiveData()

}
