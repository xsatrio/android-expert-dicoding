package com.xsat.androidexpertdicoding.core.domain.usecase

import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.core.domain.model.GithubUserDetail
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getUsers(query: String): Flow<Resource<List<GithubUsers>>>

    fun getFavoriteUsers(): Flow<List<GithubUsers>>

    suspend fun setFavoriteUser(user: GithubUsers, state: Boolean)

    fun getDetailUser(username: String): Flow<Resource<GithubUserDetail>>

}
