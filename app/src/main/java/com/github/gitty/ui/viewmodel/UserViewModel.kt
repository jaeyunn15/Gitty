package com.github.gitty.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.entity.UserInfoItem
import com.github.gitty.domain.usecase.GetUserInfoUseCase
import com.github.gitty.domain.usecase.GetUserRepositoriesUseCase
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
): BaseViewModel() {

    private val _userInfoState = MutableStateFlow(UserInfoState(false, null, null))
    val userInfoState: StateFlow<UserInfoState> get() = _userInfoState

    fun getUserInfo() = viewModelScope.launch {
        _userInfoState.update { it.copy(isLoading = true) }
        try {
            val userInfoResult = getUserInfoUseCase()
            _userInfoState.update { it.copy(userInfo = userInfoResult) }
            if (!userInfoResult.userId.isNullOrEmpty()) {
                val userRepositories = getUserRepositoriesUseCase(userInfoResult.userId)
                _userInfoState.update {
                    it.copy(
                        isLoading = false,
                        userRepositories = userRepositories
                    )
                }
            }
        } catch (e: Exception) {
            //global error handling
        }
    }


    data class UserInfoState (
        val isLoading: Boolean = false,
        val userInfo: UserInfoItem?,
        val userRepositories: List<RepositoryItem>?
    )
}