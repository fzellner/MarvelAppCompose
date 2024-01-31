package com.fzellner.marvelappcompose

import com.fzellner.marvelappcompose.domain.model.ComicEntity
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import kotlin.random.Random

object ComicListEntityFactory {

    fun createRandomComicListEntity(
        comics: List<ComicEntity> = createRandomComicEntities(),
        offset: Int = Random.nextInt(),
        count: Int = comics.size,
    ): ComicListEntity {
        return ComicListEntity(
            currentOffset = offset,
            count = count,
            comics = comics
        )
    }

    private fun createRandomComicEntities(): List<ComicEntity> {
        val numberOfComics =
            buildList {
                repeat(10) {
                    add(createRandomComicEntity())
                }
            }
        return numberOfComics
    }

    private fun createRandomComicEntity(): ComicEntity {
        return ComicEntity(
            id = Random.nextInt(),
            title = "Random Title ${Random.nextInt(100)}",
            imageUrl = "https://example.com/${Random.nextInt(100)}.jpg"
        )
    }
}
