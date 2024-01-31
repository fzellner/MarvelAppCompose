package com.fzellner.marvelappcompose.data.remote.mapper

import com.fzellner.marvelappcompose.data.remote.model.ComicDataResponse
import com.fzellner.marvelappcompose.domain.model.ComicEntity
import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.utils.Mapper
import javax.inject.Inject

class ComicListResponseToEntityMapper @Inject constructor():
    Mapper<ComicDataResponse, ComicListEntity> {

    private fun buildImagePath(path: String, extension: String) = "$path.$extension"
    override fun mapFrom(from: ComicDataResponse): ComicListEntity {
        return ComicListEntity(
            count = from.count,
            currentOffset = from.offset,
            comics = from.results.map { comicResponse ->
                ComicEntity(
                    id = comicResponse.id,
                    title = comicResponse.title,
                    imageUrl = buildImagePath(
                        comicResponse.thumbnail.path,
                        comicResponse.thumbnail.extension
                    )
                )
            }
        )
    }
}