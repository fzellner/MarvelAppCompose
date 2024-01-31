package com.fzellner.marvelappcompose.comicdetails.data.remote.mapper

import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailDateResponse
import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailResponse
import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.utils.Mapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComicDetailResponseToEntityMapper : Mapper<ComicDetailResponse, ComicDetailEntity> {
    private val WRITER_ROLE = "writer"
    override fun mapFrom(from: ComicDetailResponse): ComicDetailEntity {
        return ComicDetailEntity(
            id = from.data.results.firstOrNull()?.id ?: 0,
            title = from.data.results.firstOrNull()?.title ?: "",
            imageUrl = "${from.data.results.firstOrNull()?.thumbnail?.path}.${from.data.results.firstOrNull()?.thumbnail?.extension}",
            description = from.data.results.firstOrNull()?.textObjects?.firstOrNull()?.text ?: "",
            price = from.data.results.firstOrNull()?.prices?.maxBy { it.price }?.price ?: 0.0,
            writers = from.data.results.firstOrNull()?.creators?.items?.filter { it.role == WRITER_ROLE }
                ?.map { it.name } ?: emptyList(),
            publishedDate = publishedDateMapper(
                from.data.results.firstOrNull()?.dates
            )
        )
    }

    private fun publishedDateMapper(dateList: List<ComicDetailDateResponse>?): Date? {
        val currentDate = dateList?.filter { !it.date.contains("-0001") }?.find { it.type == "focDate" }?.date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return currentDate?.let {
            return try {
                dateFormat.parse(currentDate)
            } catch (e: Exception) {
                null
            }
        }
    }
}