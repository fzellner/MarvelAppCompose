package com.fzellner.marvelappcompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.marvelappcompose.domain.usecase.GetComicListUseCase
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import com.fzellner.marvelappcompose.presentation.mapper.ComicListEntityToUiModelMapper
import com.fzellner.marvelappcompose.presentation.model.ComicUiModel
import com.fzellner.marvelappcompose.presentation.state.ComicListViewState
import com.fzellner.marvelappcompose.presentation.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val useCase: GetComicListUseCase,
    private val mapper: ComicListEntityToUiModelMapper
) : ViewModel() {
    private val OFFSET = 20
    private val FIRST_LOAD = 0

    private val _viewState: MutableStateFlow<ComicListViewState> =
        MutableStateFlow(ComicListViewState.Loading)
    val viewState: StateFlow<ComicListViewState>
        get() = _viewState
    private val comicList = mutableListOf<ComicUiModel>()
    private var offset by mutableIntStateOf(0)
    var listState by mutableStateOf(ListState.IDLE)
    var canPaginate by mutableStateOf(false)

    init {
        viewModelScope.launch {
            getComics()
        }
    }

    fun getComics() = viewModelScope.launch {
        if (offset == FIRST_LOAD || (offset != FIRST_LOAD && canPaginate) && listState == ListState.IDLE) {
            listState = if (offset == FIRST_LOAD) ListState.LOADING else ListState.PAGINATING
            useCase(offset)
                .catch {
                    _viewState.value = ComicListViewState.Error(
                        it.message?: ""
                    )
                }
                .collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        canPaginate = result.data.comics.size == result.data.count

                        if (offset == FIRST_LOAD) {
                            comicList.clear()
                            comicList.addAll(mapper.mapFrom(result.data))
                        } else {
                            comicList.addAll(mapper.mapFrom(result.data))
                        }
                        listState = ListState.IDLE

                        if (canPaginate) {
                            offset += OFFSET
                        } else {
                            listState = ListState.PAGINATION_EXHAUST
                        }
                        _viewState.value = ComicListViewState.ShowComics(comicList)
                    }
                    is ResultWrapper.Error -> {
                        listState = ListState.ERROR
                       _viewState.value = ComicListViewState.Error(
                            result.error
                        )
                    }
                }
            }
        }
    }
}


