package com.xsat.androidexpertdicoding.core.data.source.local.room

import androidx.room.*
import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity)

    @Update
    suspend fun updateGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity)

    @Delete
    suspend fun removeGithubUser(user: com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity)

    @Query("SELECT * FROM GithubUsersEntity")
    fun getGithubUser(): Flow<List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity>>

    @Query("SELECT * FROM GithubUsersEntity WHERE username = :username")
    fun getUsername(username: String): Flow<List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity>>
}
