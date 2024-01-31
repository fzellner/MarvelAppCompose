package com.fzellner.marvelappcompose.data.remote.model

import com.squareup.moshi.JsonClass

data class ComicResponse(
    val data: ComicDataResponse
)

@JsonClass(generateAdapter = true)
data class ComicDataResponse(
    val offset: Int,
    val count: Int,
    val results: List<ComicListResponse>
)

data class ComicListResponse(
    val id: Int,
    val title: String,
    val thumbnail: ComicThumbnailResponse
)

data class ComicThumbnailResponse(
    val path: String,
    val extension: String
)
