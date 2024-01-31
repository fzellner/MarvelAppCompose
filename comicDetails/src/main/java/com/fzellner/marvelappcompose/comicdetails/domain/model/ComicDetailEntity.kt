package com.fzellner.marvelappcompose.comicdetails.domain.model

import java.util.Date

data class ComicDetailEntity(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val writers: List<String>,
    val price: Double,
    val publishedDate: Date?
)