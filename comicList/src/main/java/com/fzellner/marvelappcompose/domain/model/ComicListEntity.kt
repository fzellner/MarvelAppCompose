package com.fzellner.marvelappcompose.domain.model

data class ComicListEntity(
    val currentOffset: Int,
    val count: Int,
    val comics: List<ComicEntity>
)

data class  ComicEntity(
    val id: Int,
    val title: String,
    val imageUrl: String
)
