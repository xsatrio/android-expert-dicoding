package com.xsat.androidexpertdicoding.core.data

import com.xsat.androidexpertdicoding.core.data.source.local.LocalDataSource
import com.xsat.androidexpertdicoding.core.data.source.remote.RemoteDataSource
import com.xsat.androidexpertdicoding.core.data.source.remote.network.ApiResponseResult
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.domain.repository.IGithubUserRepository
import com.xsat.androidexpertdicoding.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GithubUserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGithubUserRepository {

    override fun getUsers(query: String): Flow<com.xsat.androidexpertdicoding.core.data.Resource<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>>> {
        return object : NetworkBoundResources<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>, List<ItemsItem>>() {
            override fun loadFromDB(): Flow<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>> {
                return localDataSource.getAllGithubUsers().map {
                    com.xsat.androidexpertdicoding.core.utils.DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<ItemsItem>>> {
                return remoteDataSource.searchUsers(query)
            }

            override suspend fun saveCallResult(data: List<ItemsItem>) {
                val githubEntities = com.xsat.androidexpertdicoding.core.utils.DataMapper.mapResponsesToEntities(data)
                githubEntities.forEach {
                    localDataSource.insertGithubUser(it)
                }
            }
        }.asFlow()
    }

    override fun getFavoriteUsers(): Flow<List<com.xsat.androidexpertdicoding.core.domain.model.GithubUsers>> {
        return localDataSource.getAllGithubUsers().map {
            com.xsat.androidexpertdicoding.core.utils.DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteUser(user: com.xsat.androidexpertdicoding.core.domain.model.GithubUsers, state: Boolean) {
        val entity = com.xsat.androidexpertdicoding.core.utils.DataMapper.mapDomainToEntity(user)
        withContext(Dispatchers.IO) {
            if (state) {
                localDataSource.insertGithubUser(entity)
            } else {
                localDataSource.deleteGithubUser(entity)
            }
        }
    }
}
