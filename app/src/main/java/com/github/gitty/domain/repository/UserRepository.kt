package com.github.gitty.domain.repository

import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.entity.UserInfoItem

interface UserRepository {
    suspend fun getUserInfo(): UserInfoItem
    suspend fun getUserRepositories(userId: String): List<RepositoryItem>
}