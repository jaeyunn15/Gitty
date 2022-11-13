package com.github.gitty.data.datasource.remote.user

import com.github.gitty.data.model.UserInfoResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponse
}