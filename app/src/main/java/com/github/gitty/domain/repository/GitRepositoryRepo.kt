package com.github.gitty.domain.repository

import com.github.gitty.domain.entity.RepositoryItem

interface GitRepositoryRepo {
    suspend fun getRepositories(q: String): List<RepositoryItem>
}