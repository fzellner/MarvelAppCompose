package com.fzellner.marvelappcompose.comicdetails.presentation.compose.route

sealed class ComicDetailsRoute(val route: String) {
    object ComicDetails: ComicDetailsRoute("comic_details/{comicId}")
}