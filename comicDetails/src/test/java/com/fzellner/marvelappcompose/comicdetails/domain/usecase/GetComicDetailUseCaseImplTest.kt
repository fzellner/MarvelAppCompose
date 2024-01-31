package com.fzellner.marvelappcompose.comicdetails.domain.usecase

import com.fzellner.marvelappcompose.comicdetails.ComicDetailEntityFactory
import com.fzellner.marvelappcompose.comicdetails.domain.repository.ComicDetailRepository
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetComicDetailUseCaseImplTest {

    private val repository = mockk<ComicDetailRepository>()
    private val useCase = GetComicDetailUseCaseImpl(repository)

    @Test
    fun `usecase should call repository`() = runTest {
        coEvery {
            repository.getComicDetail(any())
        } returns flow {
            emit(
                ResultWrapper.Success(
                    ComicDetailEntityFactory.generateRandomComicDetail()
                )
            )
        }
        useCase.invoke(1)
        coVerify {
            repository.getComicDetail(1)
        }

    }

    @Test
    fun `test getComicDetail returns expected result`() = runTest {
        val comicDetailEntity = ComicDetailEntityFactory.generateRandomComicDetail()
        val comicId = 1
        val expected = flow { emit(ResultWrapper.Success(comicDetailEntity)) }

        // Configura o comportamento do mock
        coEvery { repository.getComicDetail(comicId) } returns expected

        // Invoca o método a ser testado
        val result = useCase.invoke(comicId)

        // Verifica se o resultado é o esperado
        assertEquals(expected, result)
    }

    @Test
    fun `test getComicDetail returns error`() = runTest {
        val error = "Something goes wrong"
        val comicId = 1
        val expected = flow { emit(ResultWrapper.Error(error)) }

        // Configura o comportamento do mock
        coEvery { repository.getComicDetail(comicId) } returns expected

        // Invoca o método a ser testado
        val result = useCase.invoke(comicId)

        // Verifica se o resultado é o esperado
        assertEquals(expected, result)
    }
}
