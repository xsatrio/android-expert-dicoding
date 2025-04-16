package com.xsat.androidexpertdicoding.core.domain.usecase

import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getUsers(query: String): Flow<com.xsat.androidexpertdicoding.core.data.Resource<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>>>

    fun getFavoriteUsers(): Flow<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>>

    suspend fun setFavoriteUser(user: com.xsat.androidexpertdicoding.core.domain.model.GithubUsers, state: Boolean)

}
