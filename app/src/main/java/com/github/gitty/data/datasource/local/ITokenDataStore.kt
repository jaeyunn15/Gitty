package com.github.gitty.data.datasource.local

interface ITokenDataStore {
    fun setToken(data: String)
    fun getToken(): String
    fun clearToken()
}