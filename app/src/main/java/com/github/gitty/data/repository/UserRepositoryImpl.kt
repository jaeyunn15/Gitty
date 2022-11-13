package com.github.gitty.data.repository

import com.github.gitty.data.datasource.remote.user.UserDataSource
import com.github.gitty.data.mapper.UserInfoMapper
import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {
    override suspend fun getUserInfo(): UserInfoItem =
        userDataSource.getUserInfo().let {
            UserInfoMapper.mapFromRemote(it)
        }
}