package com.github.gitty.data.repository

import com.github.gitty.data.datasource.RepositoryDataSource
import com.github.gitty.data.mapper.RepositoryMapper
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.repository.GitRepositoryRepo
import javax.inject.Inject

class GitRepositoryRepoImpl @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource
) : GitRepositoryRepo {

    override suspend fun getRepositories(q: String): List<RepositoryItem> =
        repositoryDataSource.getRepositories(q)
            .map { response ->
                RepositoryMapper.mapFromRemote(response)
            }

}