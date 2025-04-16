package com.xsat.androidexpertdicoding.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUsersEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String,
    var avatarUrl: String? = null,
    val isFavorite: Boolean
): Parcelable