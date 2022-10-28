package com.github.gitty.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("avatar_url")
    val imgUrl: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val userId: String?,
    @SerializedName("url")
    val url: String?
)