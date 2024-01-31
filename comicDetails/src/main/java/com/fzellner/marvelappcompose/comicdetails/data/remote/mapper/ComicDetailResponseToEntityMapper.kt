package com.fzellner.marvelappcompose.comicdetails.data.remote.mapper

import com.fzellner.marvelappcompose.comicdetails.data.remote.model.ComicDetailResponse
import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.utils.Mapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComicDetailResponseToEntityMapper: Mapper<ComicDetailResponse, ComicDetailEntity> {
    private val WRITER_ROLE = "writer"
    private val DATE_FILTER = "focDate"
    override fun mapFrom(from: ComicDetailResponse): ComicDetailEntity {
        return ComicDetailEntity(
            id = from.data.results.first().id,
            title = from.data.results.first().title,
            imageUrl = "${from.data.results.first().thumbnail.path}.${from.data.results.first().thumbnail.extension}",
            description = from.data.results.first().textObjects.first().text,
            price = from.data.results.first().prices.maxBy { it.price }.price,
            writers = from.data.results.first().creators.items.filter { it.role == WRITER_ROLE }
                .map { it.name },
            publishedDate = publishedDateMapper(from.data.results.first().dates.first { it.type == DATE_FILTER }.date)
        )
    }

    private fun publishedDateMapper(date: String): Date?  {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            dateFormat.parse(date)
        } catch (e: Exception) {
            null
        }
    }
}