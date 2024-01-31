package com.fzellner.marvelappcompose.presentation.compose.navigation.route

sealed class ComicListRoute(val route: String) {
    object ComicList: ComicListRoute("comic_list")
}