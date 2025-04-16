package com.xsat.androidexpertdicoding.core.data.source.local

import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val githubDao: GithubDao) {

    fun getAllGithubUsers(): Flow<List<GithubUsersEntity>> = githubDao.getGithubUser()

    fun getFavoriteGithubUsers(): Flow<List<GithubUsersEntity>> = githubDao.getFavoriteGithubUser()

    suspend fun insertGithubUser(user: GithubUsersEntity) = githubDao.addGithubUser(user)

    suspend fun setFavoriteGithubUser(user: GithubUsersEntity, newState: Boolean) {
        user.isFavorite = newState
        githubDao.updateGithubUser(user)
    }
}
