package com.github.gitty.domain.user

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UserControllerImpl @Inject constructor() : UserController {
    override val userLoginState: StateFlow<Boolean>
        get() = _userLoginState

    private val _userLoginState = MutableStateFlow(false)

    override fun updateUserLoginState(isLogin: Boolean) {
        _userLoginState.update { isLogin }
    }

}