package com.xsat.androidexpertdicoding.di

import com.xsat.androidexpertdicoding.main.MainActivityViewModel
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserInteractor
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase
import com.xsat.androidexpertdicoding.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GithubUserUseCase> { GithubUserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}