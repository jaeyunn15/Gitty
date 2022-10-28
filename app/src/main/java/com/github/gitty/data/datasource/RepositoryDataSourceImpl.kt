package com.github.gitty.data.datasource

import com.github.gitty.data.model.RepositoryResponse
import com.github.gitty.data.service.GithubService
import javax.inject.Inject

class RepositoryDataSourceImpl @Inject constructor(
    private val githubService: GithubService
) : RepositoryDataSource {

    override suspend fun getRepositories(q: String): List<RepositoryResponse> {
        val response = githubService.getRepositories(q)
        return response.items
    }

}