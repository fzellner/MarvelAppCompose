package com.fzellner.marvelappcompose.comicdetails

import com.fzellner.marvelappcompose.comicdetails.domain.model.ComicDetailEntity
import java.util.Date
import kotlin.random.Random

object ComicDetailEntityFactory {

    fun generateRandomComicDetail(): ComicDetailEntity {
        val id = Random.nextInt(1000)
        val title = "Comic Title $id"
        val imageUrl = "https://example.com/comic$id.jpg"
        val description = "Description for Comic $id"
        val writers = listOf("Writer1", "Writer2", "Writer3")
        val price = 9.99 + Random.nextDouble() * 10.0 // Random price between 9.99 and 19.99
        val publishedDate = Date()

        return ComicDetailEntity(id, title, imageUrl, description, writers, price, publishedDate)
    }
}