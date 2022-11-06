package com.github.gitty.domain

import kotlinx.coroutines.flow.StateFlow

interface UserController {
    val userLoginState: StateFlow<Boolean>
    fun updateUserLoginState(isLogin: Boolean)
}