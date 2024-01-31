package com.fzellner.marvelappcompose.comicdetails.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.fzellner.marvelappcompose.comicdetails.ComicDetailEntityFactory
import com.fzellner.marvelappcompose.comicdetails.domain.usecase.GetComicDetailUseCase
import com.fzellner.marvelappcompose.comicdetails.presentation.mapper.ComicDetailEntityToUiModelMapper
import com.fzellner.marvelappcompose.comicdetails.presentation.model.ComicDetailUiModel
import com.fzellner.marvelappcompose.comicdetails.presentation.state.ComicDetailViewState
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicDetailViewModelTest {

    private val useCase = mockk<GetComicDetailUseCase>()
    private val mapper = mockk<ComicDetailEntityToUiModelMapper>()
    private val savedStateHandle = mockk<SavedStateHandle>()
    private lateinit var viewModel: ComicDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    @Test
    fun `test viewState returns expected result`() = runTest {
        val comicDetailEntity = ComicDetailEntityFactory.generateRandomComicDetail()
        val comicUiModel = ComicDetailUiModel(
            id = 7746,
            title = "Kimberley",
            description = "Michella",
            price = "Princeton",
            authors = "Pamela",
            publishDate = "Saskia",
            imageUrl = "Jessamyn"
        )

        coEvery { useCase(any()) } returns flow { emit(ResultWrapper.Success(comicDetailEntity)) }
        coEvery { mapper.mapFrom(any()) } returns comicUiModel
        every { savedStateHandle.get<Int>("comicId") } returns 1

        viewModel = ComicDetailViewModel(savedStateHandle, useCase, mapper)


        viewModel.viewState.test {
            assertEquals(ComicDetailViewState.ShowDetails(comicUiModel), awaitItem())
        }
    }

    @Test
    fun `viewState should returns error`() = runTest {


        coEvery { useCase(any()) } returns flow { emit(ResultWrapper.Error("")) }
        every { savedStateHandle.get<Int>("comicId") } returns 1

        viewModel = ComicDetailViewModel(savedStateHandle, useCase, mapper)


        viewModel.viewState.test {
            assertEquals(ComicDetailViewState.Error(""), awaitItem())
        }
    }
}
