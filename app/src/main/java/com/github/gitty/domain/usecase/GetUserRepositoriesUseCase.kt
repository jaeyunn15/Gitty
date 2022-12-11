package com.github.gitty.domain.usecase

import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.repository.UserRepository

class GetUserRepositoriesUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): List<RepositoryItem> {
        return userRepository.getUserRepositories(userId)
    }
}