package com.example.myapplication.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.data.entities.Article
import com.example.myapplication.R
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val uiState by mainViewModel.uiState.collectAsState(initial = MainUiState())
    val news = mainViewModel.news.collectAsLazyPagingItems()

    MainScreen(news = news, query = uiState.query, actions = mainViewModel)
}

@Composable
private fun MainScreen(
    news: LazyPagingItems<Article>,
    query: String,
    actions: MainUiActions
) {
    Scaffold(topBar = {
        SearchBar(query = query, onChangeQuery = actions::updateQuery) {
            news.refresh()
        }
    }) {
        Box {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(news) {
                    it?.apply {
                        NewsItem(
                            title = title,
                            description = description,
                            author = author ?: "",
                            publishedAt = publishedAt ?: "",
                            imageUrl = urlToImage ?: ""
                        ) {
                            actions.openUrl(url)
                        }
                    }
                }
            }
            if (news.loadState.mediator?.refresh is LoadState.Loading)
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                    )
                }
        }
    }
}

@Composable
private fun SearchBar(query: String, onChangeQuery: (String) -> Unit, onSearch: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onChangeQuery,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty())
                IconButton(onClick = { onChangeQuery("") }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
        },
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            leadingIconColor = MaterialTheme.colors.onBackground,
            trailingIconColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun NewsItem(title: String,
             author: String,
             publishedAt: String,
             imageUrl: String,
             description: String,
             onClick: () -> Unit) {
    Column(
        Modifier
            .clip(MaterialTheme.shapes.small)
            .border(
                1.dp,
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small
            )
            .clickable(
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colors.surface
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                },
                onClick = onClick
            )
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = author, color = MaterialTheme.colors.onSurface)
                Text(text = publishedAt, color = MaterialTheme.colors.primary)
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            }
            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(MaterialTheme.shapes.small),
                alignment = Alignment.Center,
                circularReveal = remember {
                    CircularReveal()
                },
                placeHolder = ImageBitmap.imageResource(id = R.drawable.image),
                previewPlaceholder = R.drawable.image
            )
        }
        Text(
            text = description,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TopBarPreview() {
    SearchBar("apple", onChangeQuery = {}, onSearch = {})
}

@Composable
@Preview(showBackground = true)
private fun NewsItemPreview() {
    NewsItem(
        title = "Hello",
        author = "Some author",
        publishedAt = "april the 5th 14:43",
        description = "It's me",
        imageUrl = "https://techcrunch.com/wp-content/uploads/2022/04/GettyImages-1312540542.jpg?w=570"
    ) {}
}