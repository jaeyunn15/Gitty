package com.github.gitty.domain.usecase

import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<UserInfoItem> {
        return flow {
            val result = userRepository.getUserInfo()
            emit(result)
        }.flowOn(ioDispatcher)
    }
}