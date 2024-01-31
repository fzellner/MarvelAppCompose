package com.fzellner.marvelappcompose.comicdetails.data.api

import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicDetailApi {
    @GET("v1/public/comics/{comicId}")
    suspend fun getComicDetails(
        @Path("comicId") comicId: Int
    ) : Response<ComicDetailResponse>
}