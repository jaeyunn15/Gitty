package com.github.gitty.domain.entity

data class RepositoryItem(
    val id: Int,
    val title: String?,
    val fullTitle: String?,
    val description: String?,
    val language: String?,
    val user: UserInfoItem?,
    val score: Int?,
    val latestUpdateTime: String?,
    val gitUrl: String?
)