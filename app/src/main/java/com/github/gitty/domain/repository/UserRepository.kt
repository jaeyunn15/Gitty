package com.github.gitty.domain.repository

import com.github.gitty.domain.entity.UserInfoItem

interface UserRepository {
    suspend fun getUserInfo(): UserInfoItem
}