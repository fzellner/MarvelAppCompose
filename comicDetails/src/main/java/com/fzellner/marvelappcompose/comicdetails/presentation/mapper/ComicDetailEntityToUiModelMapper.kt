package com.fzellner.marvelappcompose.comicdetails.presentation.mapper

import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import com.fzellner.marvelappcompose.comicdetails.presentation.model.ComicDetailUiModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComicDetailEntityToUiModelMapper {
    fun mapFrom(from: ComicDetailEntity) : ComicDetailUiModel {
        return ComicDetailUiModel(
            id = from.id,
            title = from.title,
            description = from.description,
            price = formatPrice(from.price),
            authors = from.writers.joinToString(","),
            publishDate = parsePublishedDate(from.publishedDate),
            imageUrl = from.imageUrl

        )
    }

    private fun parsePublishedDate(publishedDate: Date?): String {
       return  publishedDate?.let {
            val locale = Locale.getDefault()
            val pattern = "dd/MM/yyyy"
            SimpleDateFormat(pattern,locale).format(publishedDate)
        } ?: "Couldn't find the published date"

    }

    private fun formatPrice(price: Double): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        return currencyFormatter.format(price)
    }
}
