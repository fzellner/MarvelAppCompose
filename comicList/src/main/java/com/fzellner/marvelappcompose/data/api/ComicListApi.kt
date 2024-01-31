package com.fzellner.marvelappcompose.data.api

import com.fzellner.marvelappcompose.data.remote.model.ComicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicListApi {
    @GET("/v1/public/comics")
    suspend fun getComicList(
        @Query("offset") offset:Int
    ): Response<ComicResponse>
}