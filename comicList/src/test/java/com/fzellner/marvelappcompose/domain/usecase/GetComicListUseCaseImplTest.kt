package com.fzellner.marvelappcompose.domain.usecase

import com.fzellner.marvelappcompose.ComicListEntityFactory
import com.fzellner.marvelappcompose.domain.repository.GetComicListRepository
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetComicListUseCaseImplTest {

    private val repository = mockk<GetComicListRepository>()
    val useCase = GetComicListUseCaseImpl(repository)

    @Test
    fun `usecase should call repository`() = runTest {
        coEvery {
            repository.getComicList(any())
        } returns flow {
            emit(ResultWrapper.Success(ComicListEntityFactory.createRandomComicListEntity()))
        }

        useCase(1)

        coVerify {
            repository.getComicList(1)
        }
    }

    @Test
    fun `usecase should return comic list entity when success`() = runTest {
        val comitListEntity = ComicListEntityFactory.createRandomComicListEntity()

        val expected = flow { emit(ResultWrapper.Success(comitListEntity)) }

        coEvery { repository.getComicList(any()) } returns  expected

        val result = repository.getComicList(1)

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `usecase should return error when failed`() = runTest {
        val error = "Something goes wrong"

        val expected = flow { emit(ResultWrapper.Error(error)) }

        coEvery { repository.getComicList(any()) } returns  expected

        val result = repository.getComicList(1)

        Assert.assertEquals(expected, result)

    }
}