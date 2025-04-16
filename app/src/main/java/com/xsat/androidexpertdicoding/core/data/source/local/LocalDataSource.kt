package com.xsat.androidexpertdicoding.core.data.source.local

import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val githubDao: GithubDao) {

    fun getAllGithubUsers(): Flow<List<GithubUsersEntity>> = githubDao.getGithubUser()

    fun getGithubUserByUsername(username: String): Flow<List<GithubUsersEntity>> =
        githubDao.getUsername(username)

    suspend fun insertGithubUser(user: GithubUsersEntity) = githubDao.addGithubUser(user)

    suspend fun deleteGithubUser(user: GithubUsersEntity) = githubDao.removeGithubUser(user)

    suspend fun updateGithubUser(user: GithubUsersEntity) = githubDao.updateGithubUser(user)
}
