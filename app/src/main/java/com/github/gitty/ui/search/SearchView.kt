package com.github.gitty.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.ui.components.ShowSearchProgress


@Composable
fun SearchView(
    viewModel: SearchViewModel
) {
    var query: String by rememberSaveable { mutableStateOf("") }
    TextField(
        value = query,
        onValueChange = { value ->
            query = value
            if (value.isNotEmpty()) {
                viewModel.updateQuery(query)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun ItemList(
    viewModel: SearchViewModel
) {
    val uiState = viewModel.uiState
    val isLoading = uiState.isSearchLoading
    ShowSearchProgress(isLoadingData = isLoading)
    SearchScreenContent(uiState.repositoryList)
}

@Composable
fun SearchScreenContent(result: List<RepositoryItem>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(result) { item ->
            ItemListItem(
                item = item,
                onItemClick = {

                }
            )
        }

    }
}



@Composable
fun ItemListItem(item: RepositoryItem, onItemClick: (RepositoryItem) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(item) })
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = item.title.toString(), fontSize = 18.sp, color = Color.White)
    }
}

@Preview
@Composable
fun SampleListItem() {
    val sampleData = RepositoryItem(
        1,
        "title",
        "fullTitle",
        "description",
        "language",
        null,
        2,
        null,
        null
    )
    ItemListItem(item = sampleData, onItemClick = {})
}