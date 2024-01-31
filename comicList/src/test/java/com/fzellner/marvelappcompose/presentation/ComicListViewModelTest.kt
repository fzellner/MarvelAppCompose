package com.fzellner.marvelappcompose.presentation

import app.cash.turbine.test
import com.fzellner.marvelappcompose.ComicListEntityFactory
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.domain.usecase.GetComicListUseCase
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import com.fzellner.marvelappcompose.presentation.mapper.ComicListEntityToUiModelMapper
import com.fzellner.marvelappcompose.presentation.model.ComicUiModel
import com.fzellner.marvelappcompose.presentation.state.ComicListViewState
import com.fzellner.marvelappcompose.presentation.state.ListState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ComicListViewModelTest {


    private val getComicListUseCase: GetComicListUseCase = mockk()
    private val mapper: ComicListEntityToUiModelMapper = mockk()

    private lateinit var viewModel: ComicListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `getComics should update viewState with error when failed`() = runTest {
        val error = "Something goes wrong"

        prepareScenario(resultFailure = ResultWrapper.Error(error))
        viewModel = ComicListViewModel(useCase = getComicListUseCase, mapper = mapper)
        viewModel.viewState.test {
            assertEquals(ComicListViewState.Error(error), awaitItem())
            assertEquals(ListState.ERROR, viewModel.listState)

        }
    }

    @Test
    fun `usecase fails update viewState with error`() = runBlocking {
        val exception = Exception("Something failed!")
        coEvery {
            getComicListUseCase.invoke(any())
        } returns flow { throw exception }

        viewModel = ComicListViewModel(useCase = getComicListUseCase, mapper = mapper)
        viewModel.viewState.test {
            assertEquals(ComicListViewState.Error(exception.message ?: ""), awaitItem())
        }
    }

    @Test
    fun `getComics should update viewState with loading and comics when successful`() = runTest {
        val comicListEntity = ComicListEntityFactory.createRandomComicListEntity()
        val comicListUiModel = mutableListOf<ComicUiModel>()
        repeat(20) {
            comicListUiModel.add(ComicUiModel(id = it, title = "$it", imageUrl = "$it"))
        }
        prepareScenario(comicListEntity = comicListEntity, comicListUiModel = comicListUiModel)
        viewModel = ComicListViewModel(useCase = getComicListUseCase, mapper = mapper)
        viewModel.viewState.test {
            assertEquals(ComicListViewState.ShowComics(comicListUiModel), awaitItem())
            assertEquals(ListState.IDLE, viewModel.listState)
        }
    }

    private fun prepareScenario(
        comicListEntity: ComicListEntity = ComicListEntityFactory.createRandomComicListEntity(),
        comicListUiModel: List<ComicUiModel> = listOf(
            ComicUiModel(id = 1049, title = "Dorinda", imageUrl = "Callista"),
            ComicUiModel(id = 6670, title = "Olga", imageUrl = "Chera"),
        ),
        resultSuccess: ResultWrapper.Success<ComicListEntity> = ResultWrapper.Success(
            comicListEntity
        ),
        resultFailure: ResultWrapper.Error<String>? = null
    ) {
        coEvery {
            getComicListUseCase.invoke(any())
        } returns flow {
            resultFailure?.let {
                emit(it)
            } ?: emit(resultSuccess)
        }

        every {
            mapper.mapFrom(any())
        } returns comicListUiModel
    }

}
