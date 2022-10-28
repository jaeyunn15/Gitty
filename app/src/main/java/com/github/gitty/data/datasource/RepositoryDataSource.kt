package com.github.gitty.data.datasource

import com.github.gitty.data.model.RepositoryResponse


interface RepositoryDataSource {
    suspend fun getRepositories(q: String): List<RepositoryResponse>
}