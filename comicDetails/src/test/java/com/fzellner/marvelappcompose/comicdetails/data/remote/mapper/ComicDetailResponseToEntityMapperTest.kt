package com.fzellner.marvelappcompose.comicdetails.data.remote.mapper

import com.fzellner.marvelappcompose.comicdetails.data.remote.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ComicDetailResponseToEntityMapperTest {

    private val mapper = ComicDetailResponseToEntityMapper()

    @Test
    fun `mapFrom should correctly map from ComicDetailResponse to ComicDetailEntity`() {
        val comicDetailResponse = ComicDetailResponse(
            data = ComicDataResponse(
                results = listOf(
                    ComicDetail(
                        id = 1,
                        title = "Test Title",
                        textObjects = listOf(TextObjectsResponse("Test Type", "Test Language", "Test Text")),
                        creators = CreatorsDataResponse(listOf(CreatorResponse("Test Name", "writer"))),
                        prices = listOf(PriceResponse(9.99)),
                        dates = listOf(ComicDetailDateResponse("focDate", "2024-01-29")),
                        thumbnail = ThumbnailResponse("Test Path", "Test Extension")
                    )
                )
            )
        )

        val dateString = "2024-01-29"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }

        val comicDetailEntity = mapper.mapFrom(comicDetailResponse)

        assertEquals(1, comicDetailEntity.id)
        assertEquals("Test Title", comicDetailEntity.title)
        assertEquals("Test Text", comicDetailEntity.description)
        assertEquals("Test Path.Test Extension", comicDetailEntity.imageUrl)
        assertEquals(9.99, comicDetailEntity.price, 0.0)
        assertEquals(listOf("Test Name"), comicDetailEntity.writers)
        assertEquals(date, comicDetailEntity.publishedDate)
    }
}
