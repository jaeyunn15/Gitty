package com.github.gitty.ui.repository

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.github.gitty.navigation.Screen
import com.github.gitty.ui.search.ItemListItem
import com.github.gitty.ui.search.SearchViewModel


@Composable
fun RepositoryView(
    mainNavController: NavController,
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val uiState by searchViewModel.uiState.collectAsState()

    if (uiState.isSearchLoading) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center, propagateMinConstraints = false) {
            Text(text = "is Loading ...")
        }
    } else {
        val result = uiState.repositoryList
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(result) { item ->
                ItemListItem(
                    item = item,
                    onItemClick = {
                        mainNavController.navigate(Screen.Search.route) {
                            popUpTo(navController.graph.startDestinationId)
                        }
                    }
                )
            }

        }
    }
}