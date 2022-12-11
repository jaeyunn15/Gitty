package com.github.gitty.domain.usecase

import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserInfoItem {
        return userRepository.getUserInfo()
    }
}