package com.github.gitty.data.datasource.remote.user

import com.github.gitty.data.model.RepositoryResponse
import com.github.gitty.data.model.UserInfoResponse
import com.github.gitty.data.service.GithubService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val apiService: GithubService
): UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponse {
        return apiService.getUserInfo()
    }

    override suspend fun getUserRepositories(userId: String): List<RepositoryResponse> {
        return apiService.getUserRepositories(userId)
    }


}