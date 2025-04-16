package com.xsat.androidexpertdicoding

import android.app.Application
import com.xsat.androidexpertdicoding.core.di.databaseModule
import com.xsat.androidexpertdicoding.core.di.networkModule
import com.xsat.androidexpertdicoding.core.di.repositoryModule
import com.xsat.androidexpertdicoding.di.useCaseModule
import com.xsat.androidexpertdicoding.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}