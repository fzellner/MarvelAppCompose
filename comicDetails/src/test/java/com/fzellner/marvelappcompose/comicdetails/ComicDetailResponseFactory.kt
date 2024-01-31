package com.fzellner.marvelappcompose.comicdetails

import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDataResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetail
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailDateResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.CreatorResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.CreatorsDataResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.PriceResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.TextObjectsResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ThumbnailResponse
import java.util.Calendar
import kotlin.random.Random

object ComicDetailResponseFactory {


    fun createComidDetailResponse() = ComicDetailResponse(
        data = createComicDataResponse()
    )

    private fun createComicDataResponse() = ComicDataResponse(
        results = listOf(createComicDetail())
    )

    private fun createComicDetail() = ComicDetail(
        id = 4065,
        title = "Stefany",
        textObjects = listOf(createTextObjectsResponse()),
        creators = CreatorsDataResponse(
            items = createCreatorsDataResponse()
        ),
        prices = listOf(createPriceResponse()),
        dates = listOf(createComicDetailDateResponse()),
        thumbnail = createThumbnailResponse()

    )

    private fun createComicDetailDateResponse() = ComicDetailDateResponse(
        type = "Darcee", date = Calendar.getInstance().time.toString()
    )

    private fun createThumbnailResponse() =
        ThumbnailResponse(path = "Antoine", extension = "Lovell")

    private fun createTextObjectsResponse() =
        TextObjectsResponse(
            type = "Deseree", language = "Julius", text = "Cardell"
        )

    private fun createCreatorsDataResponse(): List<CreatorResponse> {
        val list = mutableListOf<CreatorResponse>()
        repeat(10) {
            list.add(
                createCreatorResponse(
                    name = listOf("Fulano", "Ciclano", "Beltrano").random(),
                    role = listOf("WRITER", "ARTIST").random()
                )
            )

        }

        return list
    }

    private fun createCreatorResponse(
        name: String = "Joseph Climper",
        role: String = "Writer"
    ) = CreatorResponse(name = name, role = role)


    private fun createPriceResponse() = PriceResponse(
        price = Random.nextDouble(from = 10.0, until = 50.0)
    )


}

