package com.github.gitty.ui.login

import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.usecase.RequestAccessTokenUseCase
import com.github.gitty.data.datasource.local.ITokenDataStore
import com.github.gitty.ui.base.BaseViewModel
import com.github.gitty.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val requestAccessTokenUseCase: RequestAccessTokenUseCase,
    private val iTokenDataStore: ITokenDataStore
): BaseViewModel() {

    private val _navEvent = MutableSharedFlow<LoginScreenEvent>(extraBufferCapacity = 1)
    val navEvent: SharedFlow<LoginScreenEvent> get() = _navEvent

    var isReady: Boolean = false
    val startDestination = MutableStateFlow(Screen.LOGIN.route)

    init {
        checkLoginUser()
    }

    fun getAccessToken(code: String?) = viewModelScope.launch(ioDispatcher) {
        if (!code.isNullOrEmpty()) {
            runCatching {
                requestAccessTokenUseCase(code)
            }.fold(
                onSuccess = {
                    setEvent(LoginScreenEvent.StartMain)
                },
                onFailure = {
                    //global error
                }
            )
        }
    }

    fun setEvent(event: LoginScreenEvent) = viewModelScope.launch {
        _navEvent.emit(event)
    }

    private fun checkLoginUser() {
        val token = iTokenDataStore.getToken()
        if (token.isNotEmpty()) {
            startDestination.tryEmit(Screen.MAIN.route)
        }else{
            startDestination.tryEmit(Screen.LOGIN.route)
        }
        isReady = true
    }
}