package com.fzellner.marvelappcompose.data.remote.repository

import com.fzellner.marvelappcompose.data.api.ComicListApi
import com.fzellner.marvelappcompose.data.remote.mapper.ComicListResponseToEntityMapper
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.domain.repository.GetComicListRepository
import com.fzellner.marvelappcompose.network.model.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetComicListRepositoryImpl @Inject constructor(
    private val api: ComicListApi,
    private val mapper: ComicListResponseToEntityMapper
) : GetComicListRepository {
    override suspend fun getComicList(offset:Int): Flow<ResultWrapper<ComicListEntity, String>> {
        return flow {
            val response = api.getComicList(offset)
            emit(
                when (response.isSuccessful) {
                    true -> response.body()?.let { ResultWrapper.Success(mapper.mapFrom(it.data)) }
                        ?: ResultWrapper.Error("Empty Body")

                    false -> ResultWrapper.Error("failed to fetch comics!")
                }
            )
        }
    }
}