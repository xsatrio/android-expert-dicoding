package com.xsat.androidexpertdicoding.core.domain.repository

import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.core.domain.model.GithubUserDetail
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import kotlinx.coroutines.flow.Flow

interface IGithubUserRepository {
    fun getUsers(query: String): Flow<Resource<List<GithubUsers>>>

    fun getFavoriteUsers(): Flow<List<GithubUsers>>

    suspend fun setFavoriteUser(user: GithubUsers, state: Boolean)

    fun getDetailUser(username: String): Flow<Resource<GithubUserDetail>>

}