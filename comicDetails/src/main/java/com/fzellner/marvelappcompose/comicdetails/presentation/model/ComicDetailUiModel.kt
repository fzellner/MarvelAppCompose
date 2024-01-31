package com.fzellner.marvelappcompose.comicdetails.presentation.model

data class ComicDetailUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val authors: String,
    val publishDate: String,
    val imageUrl: String
)
