package com.example.myapplication.ui.screens

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.myapplication.R
import com.example.myapplication.bl.viewModels.MainViewModel
import com.example.myapplication.data.dataObjects.Article
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

interface MainScreenActions{
    fun updateQuery(query: String)

    fun openUrl(url: String)
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val news = mainViewModel.news.collectAsLazyPagingItems()
    val query by mainViewModel.query.collectAsState("")

    MainScreenContent(news = news, query = query, actions = mainViewModel)
}

@Composable
fun MainScreenContent(news: LazyPagingItems<Article>,
                      query: String,
                      actions: MainScreenActions) {
    val onSearch = remember {
        { news.refresh() }
    }
    Scaffold(topBar = {
        SearchBar(query = query, onChangeQuery = actions::updateQuery, onSearch = onSearch)
    }) {
        Box {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background_color))
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
                    Modifier.fillMaxSize()
                        .background(colorResource(id = R.color.black).copy(0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.teal_700),
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                    )
                }
        }
    }
}

@Composable
fun SearchBar(query: String, onChangeQuery: (String) -> Unit, onSearch: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onChangeQuery,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = colorResource(id = R.color.main_text_color), fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
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
                        contentDescription = "",
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
            textColor = colorResource(id = R.color.main_text_color),
            cursorColor = colorResource(id = R.color.teal_700),
            leadingIconColor = colorResource(id = R.color.white),
            trailingIconColor = colorResource(id = R.color.white),
            backgroundColor = colorResource(id = R.color.background_color),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun NewsItem(title: String,
             author: String,
             publishedAt: String,
             imageUrl: String,
             description: String,
             onClick: () -> Unit) {
    Column(
        Modifier
            .clickable(
                indication = rememberRipple(
                    bounded = true,
                    color = colorResource(id = R.color.border_color)
                ),
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            )
            .clip(RoundedCornerShape(34f))
            .border(
                1.dp,
                colorResource(id = R.color.border_color),
                RoundedCornerShape(12.dp)
            )
            .background(colorResource(id = R.color.background_color))
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
                Text(text = author, color = colorResource(id = R.color.main_text_color))
                Text(text = publishedAt, color = colorResource(id = R.color.secondary_text_color))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.main_text_color)
                )
            }
            GlideImage(
                imageModel = imageUrl,
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(34f)),
                alignment = Alignment.Center,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.image),
                previewPlaceholder = R.drawable.image
            )
        }
        Text(
            text = description,
            color = colorResource(id = R.color.secondary_text_color)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
    SearchBar("apple", onChangeQuery = {}, onSearch = {})
}

@Composable
@Preview(showBackground = true)
fun NewsItemPreview() {
    NewsItem(
        title = "Hello",
        author = "Some author",
        publishedAt = "april the 5th 14:43",
        description = "It's me",
        imageUrl = "https://techcrunch.com/wp-content/uploads/2022/04/GettyImages-1312540542.jpg?w=570"
    ) {}
}