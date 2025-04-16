package com.xsat.androidexpertdicoding.core.data

import com.xsat.androidexpertdicoding.core.data.source.local.LocalDataSource
import com.xsat.androidexpertdicoding.core.data.source.remote.RemoteDataSource
import com.xsat.androidexpertdicoding.core.data.source.remote.network.ApiResponseResult
import com.xsat.androidexpertdicoding.core.data.source.remote.response.ItemsItem
import com.xsat.androidexpertdicoding.core.data.source.remote.response.UserResponse
import com.xsat.androidexpertdicoding.core.domain.model.GithubUsers
import com.xsat.androidexpertdicoding.core.domain.repository.IGithubUserRepository
import com.xsat.androidexpertdicoding.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GithubUserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGithubUserRepository {

    override fun getUsers(query: String): Flow<Resource<List<GithubUsers>>> {
        return object : NetworkBoundResources<List<GithubUsers>, List<ItemsItem>>() {
            override fun loadFromDB(): Flow<List<GithubUsers>> {
                return localDataSource.getAllGithubUsers().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GithubUsers>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponseResult<List<ItemsItem>>> {
                return remoteDataSource.searchUsers(query)
            }

            override suspend fun saveCallResult(data: List<ItemsItem>) {
                val githubEntities = DataMapper.mapResponsesToEntities(data)
                githubEntities.forEach {
                    localDataSource.insertGithubUser(it)
                }
            }
        }.asFlow()
    }

    override fun getFavoriteUsers(): Flow<List<GithubUsers>> {
        return localDataSource.getAllGithubUsers().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteUser(user: GithubUsers, state: Boolean) {
        val entity = DataMapper.mapDomainToEntity(user)
        withContext(Dispatchers.IO) {
            if (state) {
                localDataSource.insertGithubUser(entity)
            } else {
                localDataSource.deleteGithubUser(entity)
            }
        }
    }

    override fun getDetailUser(username: String): Flow<Resource<UserResponse>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getDetailUser(username).first()) {
                is ApiResponseResult.Success -> emit(Resource.Success(response.data))
                is ApiResponseResult.Error -> emit(Resource.Error(response.errorMessage))
                ApiResponseResult.Empty -> emit(Resource.Error("No data found"))
            }
        }
    }
}
