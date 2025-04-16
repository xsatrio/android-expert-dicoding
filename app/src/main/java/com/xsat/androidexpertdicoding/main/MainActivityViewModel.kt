package com.xsat.androidexpertdicoding.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase

class MainActivityViewModel(githubUserUseCase: GithubUserUseCase) : ViewModel() {

    val user = githubUserUseCase.getUsers("Arif").asLiveData()

}