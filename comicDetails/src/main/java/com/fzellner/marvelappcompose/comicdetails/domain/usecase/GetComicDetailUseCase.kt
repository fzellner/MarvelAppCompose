package com.fzellner.marvelappcompose.comicdetails.domain.usecase

import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetComicDetailUseCase {
   suspend operator fun invoke  (comicId: Int): Flow<ResultWrapper<ComicDetailEntity, String>>
}