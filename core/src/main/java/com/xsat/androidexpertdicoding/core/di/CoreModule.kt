package com.xsat.androidexpertdicoding.core.di

import androidx.room.Room
import com.xsat.androidexpertdicoding.core.BuildConfig
import com.xsat.androidexpertdicoding.core.data.GithubUserRepository
import com.xsat.androidexpertdicoding.core.data.source.local.LocalDataSource
import com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDatabase
import com.xsat.androidexpertdicoding.core.data.source.remote.RemoteDataSource
import com.xsat.androidexpertdicoding.core.data.source.remote.network.ApiService
import com.xsat.androidexpertdicoding.core.domain.repository.IGithubUserRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<GithubDatabase>().githubDao()
    }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("xsat93".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            GithubDatabase::class.java, "Github.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = BuildConfig.BASE_URL_CERTIFICATE
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/1EkvzibgiE3k+xdsv+7UU5vhV8kdFCQiUiFdMX5Guuk=")
            .add(hostname, "sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=")
            .add(hostname, "sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IGithubUserRepository> {
        GithubUserRepository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}