package com.fzellner.marvelappcompose.presentation.compose.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fzellner.marvelappcompose.designtoken.MarvelAppTheme
import com.fzellner.marvelappcompose.designtoken.components.ComicCardItemComponent
import com.fzellner.marvelappcompose.designtoken.components.LoadingComponent
import com.fzellner.marvelappcompose.designtoken.components.LoadingComponentType
import com.fzellner.marvelappcompose.presentation.ComicListViewModel
import com.fzellner.marvelappcompose.presentation.state.ComicListViewState
import com.fzellner.marvelappcompose.presentation.state.ListState

@Composable
fun ComicListScreen(
    onComicSelected: (comicId: Int) -> Unit,
) {
    val comicListViewModel: ComicListViewModel = hiltViewModel()
    val viewState = comicListViewModel.viewState.collectAsState().value
    val lazyGridState = rememberLazyGridState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            comicListViewModel.canPaginate
                    && (lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -5) >= (lazyGridState.layoutInfo.totalItemsCount - 10)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && comicListViewModel.listState == ListState.IDLE)
            comicListViewModel.getComics()
    }

    MarvelAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            when (viewState) {
                is ComicListViewState.Error -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = viewState.message)
                }

                ComicListViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingComponent(
                        LoadingComponentType.CIRCULAR
                    )
                }

                is ComicListViewState.ShowComics -> LazyVerticalGrid(
                    state = lazyGridState,
                    modifier = Modifier.padding(it),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(viewState.comicList) {
                            ComicCardItemComponent(
                                comicImgUrl = it.imageUrl,
                                title = it.title,
                                id = "${it.id}"
                            ) { comicId ->
                                onComicSelected.invoke(comicId)
                            }
                        }
                        item(
                            span = {
                                GridItemSpan(maxLineSpan)
                            },
                            key = comicListViewModel.listState
                        ) {
                            when (comicListViewModel.listState) {
                                ListState.LOADING,
                                ListState.PAGINATING -> {
                                    LoadingComponent()
                                }

                                else -> {}
                            }
                        }
                    }
                )
            }
        }
    }

}