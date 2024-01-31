package com.fzellner.marvelappcompose.comicdetails.data.remote.repository

import com.fzellner.marvelappcompose.comicdetails.data.api.ComicDetailApi
import com.fzellner.marvelappcompose.comicdetails.data.remote.mapper.ComicDetailResponseToEntityMapper
import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.comicdetails.domain.repository.ComicDetailRepository
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ComicDetailRepositoryImpl @Inject constructor(
    private val service: ComicDetailApi,
    private val mapper: ComicDetailResponseToEntityMapper
): ComicDetailRepository {
    override suspend fun getComicDetail(id: Int): Flow<ResultWrapper<ComicDetailEntity, String>> {
        return flow {
            val response = service.getComicDetails(id)
            emit(
                when (response.isSuccessful) {
                    true -> response.body()?.let { ResultWrapper.Success(mapper.mapFrom(it)) }
                        ?: ResultWrapper.Error("Empty Body")
                    false -> ResultWrapper.Error("failed to fetch comic detail!")
                }
            )
        }
    }
}