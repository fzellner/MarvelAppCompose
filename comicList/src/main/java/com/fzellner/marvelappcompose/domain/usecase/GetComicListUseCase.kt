package com.fzellner.marvelappcompose.domain.usecase

import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetComicListUseCase {
    suspend operator fun invoke(offset:Int) : Flow<ResultWrapper<ComicListEntity, String>>
}