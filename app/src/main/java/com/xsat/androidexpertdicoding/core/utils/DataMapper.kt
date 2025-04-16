package com.xsat.androidexpertdicoding.core.utils

import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import com.xsat.androidexpertdicoding.core.data.source.remote.response.UserResponse
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers

object DataMapper {

    fun mapResponsesToEntities(input: List<ItemsItem>): List<GithubUsersEntity> {
        return input.map {
            GithubUsersEntity(
                username = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = false
            )
        }
    }

    fun mapEntitiesToDomain(input: List<GithubUsersEntity>): List<GithubUsers> {
        return input.map {
            GithubUsers(
                username = it.username,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToEntity(input: GithubUsers): GithubUsersEntity {
        return GithubUsersEntity(
            username = input.username,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )
    }
}
