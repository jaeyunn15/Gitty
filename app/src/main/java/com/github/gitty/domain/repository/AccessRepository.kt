package com.github.gitty.domain.repository

import com.github.gitty.domain.entity.GetTokenState

interface AccessRepository {
    suspend fun getAccessToken(clientId: String, clientSecret: String, code: String): GetTokenState
}