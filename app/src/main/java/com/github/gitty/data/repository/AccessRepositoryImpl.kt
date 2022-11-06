package com.github.gitty.data.repository

import com.github.gitty.data.service.AccessService
import com.github.gitty.di.NetworkModule
import com.github.gitty.domain.entity.GetTokenState
import com.github.gitty.domain.repository.AccessRepository
import javax.inject.Inject

class AccessRepositoryImpl @Inject constructor(
    @NetworkModule.typeAccess private val accessService: AccessService
): AccessRepository {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): GetTokenState {
        val result = accessService.getAccessToken(clientId, clientSecret, code)
        return if (result.isSuccessful) {
            val accessToken = result.body()?.access_token
            accessToken?.let {
                //
            }
            GetTokenState(true)
        } else {
            GetTokenState(false)
        }
    }

}