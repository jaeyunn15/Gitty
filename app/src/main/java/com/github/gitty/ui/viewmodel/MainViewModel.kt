package com.github.gitty.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.usecase.GetUserInfoUseCase
import com.github.gitty.domain.usecase.RequestAccessTokenUseCase
import com.github.gitty.domain.user.UserController
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val requestAccessTokenUseCase: RequestAccessTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {

    @Inject
    lateinit var userController: UserController

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState

    fun getAccessToken(code: String) = viewModelScope.launch(ioDispatcher) {
        val result = requestAccessTokenUseCase(code)
        updateUserLoginState(result.isSuccess)
        _loginState.update { result.isSuccess }
    }

    private fun updateUserLoginState(isLogin: Boolean) {
        userController.updateUserLoginState(isLogin)
    }

    fun logOut() {
        userController.updateUserLoginState(false)
    }
}