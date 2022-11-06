package com.github.gitty.domain.usecase

import com.github.gitty.BuildConfig
import com.github.gitty.domain.entity.GetTokenState
import com.github.gitty.domain.repository.AccessRepository
import javax.inject.Inject

class RequestAccessTokenUseCase @Inject constructor(
    private val accessRepository: AccessRepository
) {
    suspend operator fun invoke(code: String): GetTokenState {
        return accessRepository.getAccessToken(
            clientId = BuildConfig.GITHUB_CLIENT_ID,
            clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
            code = code
        )
    }
}