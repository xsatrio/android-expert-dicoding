package com.xsat.androidexpertdicoding.core.domain.usecase

import com.xsat.androidexpertdicoding.core.data.Resource
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.domain.repository.IGithubUserRepository
import kotlinx.coroutines.flow.Flow

class GithubUserInteractor(private val githubUserRepository: IGithubUserRepository) : GithubUserUseCase {
    override fun getUsers(query: String): Flow<Resource<List<GithubUsers>>> {
        return githubUserRepository.getUsers(query)
    }

    override fun getFavoriteUsers(): Flow<List<GithubUsers>> {
        return githubUserRepository.getFavoriteUsers()
    }

    override suspend fun setFavoriteUser(user: GithubUsers, state: Boolean) {
        githubUserRepository.setFavoriteUser(user, state)
    }
}
