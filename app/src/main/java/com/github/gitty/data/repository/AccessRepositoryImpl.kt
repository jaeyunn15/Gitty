package com.github.gitty.data.repository

import com.github.gitty.data.datasource.local.LocalDataSource
import com.github.gitty.data.service.AccessService
import com.github.gitty.di.NetworkModule
import com.github.gitty.domain.entity.GetTokenState
import com.github.gitty.domain.repository.AccessRepository
import javax.inject.Inject

class AccessRepositoryImpl @Inject constructor(
    @NetworkModule.typeAccess private val accessService: AccessService,
    private val localDataSource: LocalDataSource
): AccessRepository {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): GetTokenState {
        val result = accessService.getAccessToken(clientId, clientSecret, code)
        return if (result.isSuccessful) {
            val accessToken = result.body()?.access_token
            if (!accessToken.isNullOrEmpty()) {
                localDataSource.saveToken(accessToken)
                GetTokenState(true, accessToken)
            } else {
                GetTokenState(false)
            }
        } else {
            GetTokenState(false)
        }
    }

}