package com.github.gitty.domain.user

import kotlinx.coroutines.flow.StateFlow

interface UserController {
    val userLoginState: StateFlow<Boolean>
    fun updateUserLoginState(isLogin: Boolean)
}