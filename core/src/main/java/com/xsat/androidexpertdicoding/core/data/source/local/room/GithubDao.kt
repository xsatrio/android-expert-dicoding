package com.xsat.androidexpertdicoding.core.data.source.local.room

import androidx.room.*
import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGithubUser(user: GithubUsersEntity)

    @Update
    suspend fun updateGithubUser(user: GithubUsersEntity)

    @Delete
    suspend fun removeGithubUser(user: GithubUsersEntity)

    @Query("SELECT * FROM GithubUsersEntity")
    fun getGithubUser(): Flow<List<GithubUsersEntity>>

    @Query("SELECT * FROM GithubUsersEntity WHERE isFavorite = 1")
    fun getFavoriteGithubUser(): Flow<List<GithubUsersEntity>>
}
