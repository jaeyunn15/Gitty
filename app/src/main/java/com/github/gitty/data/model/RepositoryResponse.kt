package com.github.gitty.data.model

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("full_name")
    val full_title: String?,
    @SerializedName("git_url")
    val git_url: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String?,
    @SerializedName("name")
    val title: String?,
    @SerializedName("owner")
    val user: UserInfoResponse?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("updated_at")
    val latestUpdateTime: String?
)

