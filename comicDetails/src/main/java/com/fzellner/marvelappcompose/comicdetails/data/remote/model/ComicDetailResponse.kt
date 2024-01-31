package com.fzellner.marvelappcompose.comicdetails.data.remote.model

data class ComicDetailResponse(
    val data: ComicDataResponse
)

data class ComicDataResponse(
    val results: List<ComicDetail>
)

data class ComicDetail(
    val id: Int,
    val title: String,
    val textObjects: List<TextObjectsResponse>,
    val creators: CreatorsDataResponse,
    val prices: List<PriceResponse>,
    val dates : List<ComicDetailDateResponse>,
    val thumbnail: ThumbnailResponse
)

data class  ComicDetailDateResponse(
    val type: String,
    val date: String,
)

data class ThumbnailResponse(
    val path: String,
    val extension: String
)

data class TextObjectsResponse(
    val type: String,
    val language: String,
    val text: String
)

data class CreatorsDataResponse(
    val items: List<CreatorResponse>
)

data class CreatorResponse(
    val name: String,
    val role: String
)

data class PriceResponse(
    val price: Double
)

