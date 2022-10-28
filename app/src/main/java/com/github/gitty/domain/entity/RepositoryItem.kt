package com.github.gitty.domain.entity

import com.github.gitty.data.model.UserInfoResponse
import com.google.gson.annotations.SerializedName

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