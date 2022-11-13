package com.github.gitty.domain.entity

data class GetTokenState(
    val isSuccess: Boolean,
    val token: String? = null
)