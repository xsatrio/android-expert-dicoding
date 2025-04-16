package com.xsat.androidexpertdicoding.di

import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserInteractor
import com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase> { GithubUserInteractor(get()) }
}

val viewModelModule = module {
//    viewModel { HomeViewModel(get()) }
//    viewModel { FavoriteViewModel(get()) }
//    viewModel { DetailTourismViewModel(get()) }
}