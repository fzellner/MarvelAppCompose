package com.fzellner.marvelappcompose.presentation.mapper

import com.fzellner.marvelappcompose.domain.model.ComicListEntity
import com.fzellner.marvelappcompose.utils.Mapper
import com.fzellner.marvelappcompose.presentation.model.ComicUiModel
import javax.inject.Inject

class ComicListEntityToUiModelMapper @Inject constructor() :
    Mapper<ComicListEntity, List<ComicUiModel>> {
    override fun mapFrom(from: ComicListEntity): List<ComicUiModel> {
        return from.comics.map {
            ComicUiModel(
                id = it.id, title = it.title, imageUrl = it.imageUrl
            )
        }
    }
}