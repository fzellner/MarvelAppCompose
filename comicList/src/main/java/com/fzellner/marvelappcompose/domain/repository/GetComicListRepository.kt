package com.fzellner.marvelappcompose.domain.repository

import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GetComicListRepository {
    suspend fun getComicList(offset: Int): Flow<ResultWrapper<ComicListEntity, String>>
}