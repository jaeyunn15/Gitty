package com.github.gitty.data.datasource.local

interface LocalDataSource {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}