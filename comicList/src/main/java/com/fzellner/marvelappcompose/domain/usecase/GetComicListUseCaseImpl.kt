package com.fzellner.marvelappcompose.domain.usecase

import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.domain.repository.GetComicListRepository
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetComicListUseCaseImpl @Inject constructor(private val repository: GetComicListRepository) :
    GetComicListUseCase {
    override suspend fun invoke(offset: Int): Flow<ResultWrapper<ComicListEntity, String>> {
        return repository.getComicList(offset)
    }
}