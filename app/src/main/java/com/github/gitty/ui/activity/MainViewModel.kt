package com.github.gitty.ui.activity

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.usecase.GetRepositoriesUseCase
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getRepositoriesUseCase: GetRepositoriesUseCase
): BaseViewModel() {

    fun getSearchRepository() {
        getRepositoriesUseCase("kotlin").onEach {

        }.flowOn(ioDispatcher).launchIn(viewModelScope)
    }
}