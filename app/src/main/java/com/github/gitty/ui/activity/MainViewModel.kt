package com.github.gitty.ui.activity

import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.UserController
import com.github.gitty.domain.UserControllerImpl
import com.github.gitty.domain.usecase.RequestAccessTokenUseCase
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val requestAccessTokenUseCase: RequestAccessTokenUseCase
): BaseViewModel() {

    @Inject
    lateinit var userController: UserController

    suspend fun getAccessToken(code: String) =
        withContext(ioDispatcher) {
            val result = requestAccessTokenUseCase(code)
            userController.updateUserLoginState(result.isSuccess)
        }

    fun logOut() {
        userController.updateUserLoginState(false)
    }
}