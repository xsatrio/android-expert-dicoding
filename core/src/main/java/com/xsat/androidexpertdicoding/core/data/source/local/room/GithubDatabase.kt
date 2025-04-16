package com.xsat.androidexpertdicoding.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity

@Database(entities = [com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase: RoomDatabase() {

    abstract fun githubDao(): com.xsat.androidexpertdicoding.core.data.source.local.room.GithubDao

}