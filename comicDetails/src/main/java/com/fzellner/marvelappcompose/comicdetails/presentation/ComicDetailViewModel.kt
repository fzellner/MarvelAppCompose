package com.fzellner.marvelappcompose.comicdetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.marvelappcompose.comicdetails.domain.usecase.GetComicDetailUseCase
import com.fzellner.marvelappcompose.comicdetails.presentation.mapper.ComicDetailEntityToUiModelMapper
import com.fzellner.marvelappcompose.comicdetails.presentation.state.ComicDetailViewState
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComicDetailUseCase: GetComicDetailUseCase,
    private val mapper: ComicDetailEntityToUiModelMapper
) : ViewModel() {

    private val _viewState =
        MutableStateFlow<ComicDetailViewState>(ComicDetailViewState.Loading)
    val viewState: StateFlow<ComicDetailViewState>
        get() = _viewState

    private val comicId: Int = checkNotNull(savedStateHandle["comicId"])


    init {
        viewModelScope.launch {
            getComicDetailUseCase(comicId)
                .catch {
                    _viewState.value =
                        ComicDetailViewState.Error(it.message ?: "Something goes wrong")
                }
                .collect { result ->
                    _viewState.value = when (result) {
                        is ResultWrapper.Success -> ComicDetailViewState.ShowDetails(
                            mapper.mapFrom(
                                result.data
                            )
                        )

                        is ResultWrapper.Error -> ComicDetailViewState.Error(result.error)
                    }
                }
        }
    }

}