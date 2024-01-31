package com.fzellner.marvelappcompose.comicdetails.presentation.state

import com.fzellner.marvelappcompose.comicdetails.presentation.model.ComicDetailUiModel

sealed class ComicDetailViewState {
    object Loading : ComicDetailViewState()
    data class Error(val message: String) : ComicDetailViewState()
    data class ShowDetails(val comicDetailUiModel: ComicDetailUiModel) : ComicDetailViewState()
}