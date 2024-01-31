package com.fzellner.marvelappcompose.comicdetails.data.remote.repository

import com.fzellner.marvelappcompose.comicdetails.ComicDetailEntityFactory
import com.fzellner.marvelappcompose.comicdetails.ComicDetailResponseFactory
import com.fzellner.marvelappcompose.comicdetails.data.api.ComicDetailApi
import com.fzellner.marvelappcompose.comicdetails.data.remote.mapper.ComicDetailResponseToEntityMapper
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailResponse
import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class ComicDetailRepositoryImplTest {

    private val api: ComicDetailApi = mockk()
    private val mapper: ComicDetailResponseToEntityMapper = mockk()

    private val repository = ComicDetailRepositoryImpl(api, mapper)


    @Test
    fun `get comicDetail from service should return sucess`() = runTest {
        val comicDetailResponse = ComicDetailResponseFactory.createComidDetailResponse()
        val comicDetailEntity = ComicDetailEntityFactory.generateRandomComicDetail()
        prepareScenario(comicDetailResponse, comicDetailEntity)
        val result = repository.getComicDetail(1).single()
        Assert.assertTrue(result is ResultWrapper.Success)
        Assert.assertEquals(comicDetailEntity, (result as ResultWrapper.Success).data)
    }

    @Test
    fun `get comicDetail from service with empty response return failure`() = runTest {
        coEvery {
            api.getComicDetails(any())
        } coAnswers {
            Response.success(null)
        }
        val result = repository.getComicDetail(1).single()
        Assert.assertTrue(result is ResultWrapper.Error)
        Assert.assertEquals("Empty Body", (result as ResultWrapper.Error).error)
    }

    @Test
    fun `get comicDetail from service with error return failure`() = runTest {
        coEvery { api.getComicDetails(any()) } coAnswers {
            Response.error(400, "".toResponseBody())
        }
        val result = repository.getComicDetail(1).first()
        Assert.assertTrue(result is ResultWrapper.Error)
        Assert.assertEquals("failed to fetch comic detail!", (result as ResultWrapper.Error).error)
    }

    private fun prepareScenario(
        comicDetailResponse: ComicDetailResponse = ComicDetailResponseFactory.createComidDetailResponse(),
        comicDetailEntity: ComicDetailEntity = ComicDetailEntityFactory.generateRandomComicDetail()
    ) {

        coEvery {
            api.getComicDetails(any())
        } coAnswers {
            Response.success(
                comicDetailResponse
            )
        }

        every {
            mapper.mapFrom(any())
        } returns  comicDetailEntity
    }
}