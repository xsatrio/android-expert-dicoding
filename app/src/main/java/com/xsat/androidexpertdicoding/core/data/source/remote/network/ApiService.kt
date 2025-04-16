package com.xsat.androidexpertdicoding.core.data.source.remote.network

import com.xsat.androidexpertdicoding.BuildConfig
import com.xsat.androidexpertdicoding.core.data.source.remote.response.UserResponse
import com.xsat.androidexpertdicoding.core.data.source.remote.response.GithubResponse
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers(AUTHORIZATION_HEADER)
    fun searchUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers(AUTHORIZATION_HEADER)
    fun getDetailUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/{type}")
    @Headers(AUTHORIZATION_HEADER)
    fun getUserFollow(@Path("username") username: String, @Path("type") type: String): Call<List<ItemsItem>>

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization: token ${BuildConfig.API_KEY}"
    }
}