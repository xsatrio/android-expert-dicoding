package com.xsat.androidexpertdicoding.core.utils

import com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers

object DataMapper {

    fun mapResponsesToEntities(input: List<ItemsItem>): List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity> {
        return input.map {
            com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity(
                username = it.login,
                avatarUrl = it.avatarUrl,
                isFavorite = false
            )
        }
    }

    fun mapEntitiesToDomain(input: List<com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity>): List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers> {
        return input.map {
            com.xsat.androidexpertdicoding.core.domain.model.GithubUsers(
                username = it.username,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapDomainToEntity(input: com.xsat.androidexpertdicoding.core.domain.model.GithubUsers): com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity {
        return com.xsat.androidexpertdicoding.core.data.source.local.entity.GithubUsersEntity(
            username = input.username,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )
    }
}
