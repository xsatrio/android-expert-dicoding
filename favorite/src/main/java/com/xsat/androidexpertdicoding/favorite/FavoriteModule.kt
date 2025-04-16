package com.xsat.androidexpertdicoding.favorite

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
    viewModel {
        FavouriteViewModel(get())
    }
}