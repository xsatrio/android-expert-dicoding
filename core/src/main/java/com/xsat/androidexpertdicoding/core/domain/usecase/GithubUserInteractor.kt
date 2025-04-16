package com.xsat.androidexpertdicoding.core.domain.usecase

import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.domain.repository.IGithubUserRepository
import kotlinx.coroutines.flow.Flow

class GithubUserInteractor(private val githubUserRepository: IGithubUserRepository) :
    com.xsat.androidexpertdicoding.core.domain.usecase.GithubUserUseCase {
    override fun getUsers(query: String): Flow<com.xsat.androidexpertdicoding.core.data.Resource<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>>> {
        return githubUserRepository.getUsers(query)
    }

    override fun getFavoriteUsers(): Flow<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>> {
        return githubUserRepository.getFavoriteUsers()
    }

    override suspend fun setFavoriteUser(user: com.xsat.androidexpertdicoding.core.domain.model.GithubUsers, state: Boolean) {
        githubUserRepository.setFavoriteUser(user, state)
    }
}
