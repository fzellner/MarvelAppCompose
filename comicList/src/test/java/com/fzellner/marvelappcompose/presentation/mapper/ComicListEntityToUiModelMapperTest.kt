package com.fzellner.marvelappcompose.presentation.mapper

import com.fzellner.marvelappcompose.domain.model.ComicEntity
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.presentation.model.ComicUiModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ComicListEntityToUiModelMapperTest {

    private val mockComicListEntity = mockk<ComicListEntity>()

    private val mapper = ComicListEntityToUiModelMapper()

    @Test
    fun `mapFrom should correctly map ComicListEntity to List of ComicUiModel`() {
        val mockComics = listOf(
            ComicEntity(id = 1, title = "Comic 1", imageUrl = "path1.jpg"),
            ComicEntity(id = 2, title = "Comic 2", imageUrl = "path2.jpg")
        )

        every { mockComicListEntity.comics } returns mockComics

        val result = mapper.mapFrom(mockComicListEntity)

        val expectedResult = listOf(
            ComicUiModel(id = 1, title = "Comic 1", imageUrl = "path1.jpg"),
            ComicUiModel(id = 2, title = "Comic 2", imageUrl = "path2.jpg")
        )

        // Assert the result
        assertEquals(expectedResult, result)
    }
}
