package com.fzellner.marvelappcompose.comicdetails.domain.repository

import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.network.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface ComicDetailRepository {
    suspend fun getComicDetail(id:Int): Flow<ResultWrapper<ComicDetailEntity, String>>
}