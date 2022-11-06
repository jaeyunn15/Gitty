package com.github.gitty.data.model

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val token_type: String
)