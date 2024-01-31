package com.fzellner.marvelappcompose.data.remote.mapper

import com.fzellner.marvelappcompose.data.remote.model.ComicDataResponse
import com.fzellner.marvelappcompose.data.remote.model.ComicListResponse
import com.fzellner.marvelappcompose.data.remote.model.ComicThumbnailResponse
import com.fzellner.marvelappcompose.domain.model.ComicEntity
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.network.utils.Mapper
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ComicListResponseToEntityMapperTest {

    private val mockComicDataResponse = mockk<ComicDataResponse>()

    private val mapper = ComicListResponseToEntityMapper()

    @Test
    fun `mapFrom should correctly map ComicDataResponse to ComicListEntity`() {
        val mockResults = listOf(
            ComicListResponse(id = 1, title = "Comic 1", thumbnail = ComicThumbnailResponse("path1", "jpg")),
            ComicListResponse(id = 2, title = "Comic 2", thumbnail = ComicThumbnailResponse("path2", "jpg")),
        )

        every { mockComicDataResponse.count } returns 2
        every { mockComicDataResponse.offset } returns 0
        every { mockComicDataResponse.results } returns mockResults

        val result = mapper.mapFrom(mockComicDataResponse)

        val expectedResult = ComicListEntity(
            count = 2,
            currentOffset = 0,
            comics = listOf(
                ComicEntity(id = 1, title = "Comic 1", imageUrl = "path1.jpg"),
                ComicEntity(id = 2, title = "Comic 2", imageUrl = "path2.jpg")
            )
        )

        assertEquals(expectedResult, result)
    }
}
