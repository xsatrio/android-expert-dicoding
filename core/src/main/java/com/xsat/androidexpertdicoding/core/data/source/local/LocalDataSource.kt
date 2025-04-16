package com.xsat.androidexpertdicoding.core.data.source.local

import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val githubDao: com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDao) {

    fun getAllGithubUsers(): Flow<List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity>> = githubDao.getGithubUser()

    fun getGithubUserByUsername(username: String): Flow<List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity>> =
        githubDao.getUsername(username)

    suspend fun insertGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity) = githubDao.addGithubUser(user)

    suspend fun deleteGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity) = githubDao.removeGithubUser(user)

    suspend fun updateGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity) = githubDao.updateGithubUser(user)
}
