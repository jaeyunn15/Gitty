package com.github.gitty.ui.activity

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.domain.usecase.GetUserInfoUseCase
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {

    private val _userInfoState = MutableStateFlow<UserInfoState>(UserInfoState(false, null))
    val userInfoState: StateFlow<UserInfoState> get() = _userInfoState


    fun getUserInfo() {
        _userInfoState.update { it.copy(isLoading = true) }
        getUserInfoUseCase().onEach { item ->
            _userInfoState.update {
                it.copy(
                    isLoading = false,
                    userInfo = item
                )
            }
        }.flowOn(ioDispatcher).launchIn(viewModelScope)
    }

    data class UserInfoState (
        val isLoading: Boolean = false,
        val userInfo: UserInfoItem?
    )
}