package com.github.gitty.ui.search

import androidx.lifecycle.viewModelScope
import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.usecase.GetRepositoriesUseCase
import com.github.gitty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getRepositoriesUseCase: GetRepositoriesUseCase
) : BaseViewModel() {

    var uiState = MutableStateFlow<SearchViewState>(SearchViewState())

    private val _searchQuery = MutableStateFlow("")

    fun updateQuery(query: String) {
        _searchQuery.update { query }
    }

    init {
        performQuery()
        updateQuery("android")
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun performQuery() {
        _searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                if (query.isNotEmpty()) {
                    uiState.update {
                        it.copy(isSearchLoading = true)
                    }
                    getRepositoriesUseCase(query)
                } else {
                    flow { emit(emptyList()) }
                }
            }
            .onEach { result ->
                uiState.update {
                    it.copy(
                        repositoryList = result,
                        isSearchLoading = false
                    )
                }
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }
}

data class SearchViewState(
    val repositoryList: List<RepositoryItem> = listOf(),
    val isSearchLoading: Boolean = false
)

