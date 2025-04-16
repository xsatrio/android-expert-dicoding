package com.xsat.androidexpertdicoding.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUsers (
    var username: String,
    var avatarUrl: String? = null,
    var isFavorite: Boolean
) : Parcelable