package com.fzellner.marvelappcompose.presentation.state

import com.fzellner.marvelappcompose.presentation.model.ComicUiModel


sealed class ComicListViewState {
    object Loading : ComicListViewState()
    data class Error(val message: String) : ComicListViewState()
    data class ShowComics(val comicList: List<ComicUiModel>) : ComicListViewState()
}