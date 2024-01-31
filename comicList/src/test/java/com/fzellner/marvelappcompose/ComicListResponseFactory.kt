package com.fzellner.marvelappcompose

import com.fzellner.marvelappcompose.data.remote.model.ComicDataResponse
import com.fzellner.marvelappcompose.data.remote.model.ComicListResponse
import com.fzellner.marvelappcompose.data.remote.model.ComicResponse
import com.fzellner.marvelappcompose.data.remote.model.ComicThumbnailResponse
import kotlin.random.Random

object ComicListResponseFactory {

    fun createRandomComicResponse(): ComicResponse {
        return ComicResponse(createRandomComicDataResponse())
    }

    private fun createRandomComicDataResponse(): ComicDataResponse {
        return ComicDataResponse(
            offset = Random.nextInt(),
            count = Random.nextInt(),
            results = createRandomComicListResponses()
        )
    }

    private fun createRandomComicListResponses(): List<ComicListResponse> {
        val numberOfComics = Random.nextInt(1, 10) // You can adjust the range as needed
        return List(numberOfComics) { createRandomComicListResponse() }
    }

    private fun createRandomComicListResponse(): ComicListResponse {
        return ComicListResponse(
            id = Random.nextInt(),
            title = "Random Title ${Random.nextInt(100)}",
            thumbnail = createRandomComicThumbnailResponse()
        )
    }

    private fun createRandomComicThumbnailResponse(): ComicThumbnailResponse {
        return ComicThumbnailResponse(
            path = "https://example.com/${Random.nextInt(100)}",
            extension = "jpg"
        )
    }
}
