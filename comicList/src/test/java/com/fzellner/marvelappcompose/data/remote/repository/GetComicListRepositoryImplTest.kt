package com.fzellner.marvelappcompose.data.remote.repository

import com.fzellner.marvelappcompose.ComicListEntityFactory
import com.fzellner.marvelappcompose.ComicListResponseFactory
import com.fzellner.marvelappcompose.data.api.ComicListApi
import com.fzellner.marvelappcompose.data.remote.mapper.ComicListResponseToEntityMapper
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class GetComicListRepositoryImplTest {

    private val api = mockk<ComicListApi>()
    private val mapper = mockk<ComicListResponseToEntityMapper>()
    private val repository = GetComicListRepositoryImpl(api, mapper)

    @Test
    fun `getComicList returns ResultWrapper Success when api call is successful`() = runBlocking {
        val offset = 0
        val expected = ComicListEntityFactory.createRandomComicListEntity()

        coEvery { api.getComicList(any()) } coAnswers {
            Response.success(ComicListResponseFactory.createRandomComicResponse())
        }
        every {
            mapper.mapFrom(any())
        } returns expected
        val result = repository.getComicList(offset).first()
        Assert.assertTrue(result is ResultWrapper.Success)
        Assert.assertEquals(expected, (result as ResultWrapper.Success).data)
    }

    @Test
    fun `getComicList returns ResultWrapper Success with empty body api returns error`() = runBlocking {
        val offset = 0
        val expected = ComicListEntityFactory.createRandomComicListEntity()

        coEvery { api.getComicList(any()) } coAnswers {
            Response.success(null)
        }
        every {
            mapper.mapFrom(any())
        } returns expected
        val result = repository.getComicList(offset).first()
        Assert.assertTrue(result is ResultWrapper.Error)
        Assert.assertEquals("Empty Body", (result as ResultWrapper.Error).error)
    }

    @Test
    fun `getComicList returns ResultWrapper Error when api call is not successful`() = runBlocking {
        val offset = 0
        coEvery { api.getComicList(any()) } coAnswers {
            Response.error(400, "".toResponseBody())
        }
        val result = repository.getComicList(offset).first()
        Assert.assertTrue(result is ResultWrapper.Error)
        Assert.assertEquals("failed to fetch comics!", (result as ResultWrapper.Error).error)
    }
}
