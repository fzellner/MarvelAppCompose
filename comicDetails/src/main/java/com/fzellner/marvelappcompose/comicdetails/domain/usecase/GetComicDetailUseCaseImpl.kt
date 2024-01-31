package com.fzellner.marvelappcompose.comicdetails.domain.usecase

import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.comicdetails.domain.repository.ComicDetailRepository
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComicDetailUseCaseImpl @Inject constructor(
    private val repository: ComicDetailRepository
) : GetComicDetailUseCase {
    override suspend fun invoke(comicId: Int): Flow<ResultWrapper<ComicDetailEntity, String>> {
        return repository.getComicDetail(comicId)
    }
}