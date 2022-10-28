package com.github.gitty.data.model

import com.google.gson.annotations.SerializedName

data class RepositorySearchResponse(
    @SerializedName("items")
    val items: List<RepositoryResponse>,
    @SerializedName("total_count")
    val total_count: Int
)