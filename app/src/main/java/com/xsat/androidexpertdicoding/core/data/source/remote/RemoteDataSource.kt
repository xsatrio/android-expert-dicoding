package com.xsat.androidexpertdicoding.core.data.source.remote

import android.util.Log
import com.xsat.androidexpertdicoding.core.data.source.remote.network.ApiResponseResult
import com.xsat.androidexpertdicoding.core.data.source.remote.network.ApiService
import com.xsat.androidexpertdicoding.core.data.source.remote.response.UserResponse
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun searchUsers(query: String): Flow<ApiResponseResult<List<ItemsItem>>> {
        return flow {
            try {
                val response = apiService.searchUsers(query).execute()
                if (response.isSuccessful) {
                    val data = response.body()?.items
                    if (!data.isNullOrEmpty()) {
                        emit(ApiResponseResult.Success(data))
                    } else {
                        emit(ApiResponseResult.Empty)
                    }
                } else {
                    emit(ApiResponseResult.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ApiResponseResult.Error(e.toString()))
                Log.e("RemoteDataSource", "searchUsers: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String): Flow<ApiResponseResult<UserResponse>> {
        return flow {
            try {
                val response = apiService.getDetailUser(username).execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(ApiResponseResult.Success(body))
                    } else {
                        emit(ApiResponseResult.Empty)
                    }
                } else {
                    emit(ApiResponseResult.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ApiResponseResult.Error(e.toString()))
                Log.e("RemoteDataSource", "getDetailUser: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollow(username: String, type: String): Flow<ApiResponseResult<List<ItemsItem>>> {
        return flow {
            try {
                val response = apiService.getUserFollow(username, type).execute()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        emit(ApiResponseResult.Success(data))
                    } else {
                        emit(ApiResponseResult.Empty)
                    }
                } else {
                    emit(ApiResponseResult.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ApiResponseResult.Error(e.toString()))
                Log.e("RemoteDataSource", "getUserFollow: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }
}
