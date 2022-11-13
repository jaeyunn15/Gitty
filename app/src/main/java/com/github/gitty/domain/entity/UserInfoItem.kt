package com.github.gitty.domain.entity

data class UserInfoItem(
    val id: Int,
    val userId: String?,
    val url: String?,
    val imgUrl: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val localization: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val bio: String? = null,
)