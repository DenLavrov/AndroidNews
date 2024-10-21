package com.example.myapplication.main.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myapplication.R
import com.example.myapplication.main.data.model.Article
import com.example.myapplication.main.presentation.utils.openUri
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val news = state.data.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.dispatch(MainAction.Init)
        viewModel.effects.collect {
            if (it is MainEffect.Refresh)
                if (news.loadState.refresh is LoadState.Error)
                    news.retry()
                else
                    news.refresh()
        }
    }

    MainScreen(state, news, viewModel::dispatch)
}

@Composable
private fun MainScreen(
    state: MainState,
    news: LazyPagingItems<Article>,
    dispatch: (MainAction) -> Unit
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        SearchBar(query = state.query, onChangeQuery = { dispatch(MainAction.UpdateQuery(it)) })
    }) { padding ->
        Box(Modifier.padding(padding)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(news.itemCount, key = { news[it]!!.id }) {
                    news[it]?.apply {
                        NewsItem(
                            title = title,
                            description = description,
                            author = author.orEmpty(),
                            publishedAt = publishedAt.orEmpty(),
                            imageUrl = urlToImage.orEmpty()
                        ) {
                            context.openUri(url)
                        }
                    }
                }
                if (state.isLoading)
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.onPrimary,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    }
            }
        }
    }
}

@Composable
private fun SearchBar(query: String, onChangeQuery: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onChangeQuery,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                painterResource(R.drawable.search_icon),
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
                        painterResource(R.drawable.clear_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
        },
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
private fun NewsItem(
    title: String,
    author: String,
    publishedAt: String,
    imageUrl: String,
    description: String,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .clip(MaterialTheme.shapes.small)
            .border(
                1.dp,
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small
            )
            .clickable(onClick = onClick)
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
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small),
                alignment = Alignment.Center,
                circularReveal = CircularReveal(),
                placeHolder = painterResource(R.drawable.image),
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
    SearchBar("apple", onChangeQuery = {})
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