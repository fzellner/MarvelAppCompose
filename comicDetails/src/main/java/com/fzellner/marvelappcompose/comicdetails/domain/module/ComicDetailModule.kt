package com.fzellner.marvelappcompose.comicdetails.domain.module

import com.fzellner.marvelappcompose.comicdetails.data.api.ComicDetailApi
import com.fzellner.marvelappcompose.comicdetails.data.remote.mapper.ComicDetailResponseToEntityMapper
import com.fzellner.marvelappcompose.comicdetails.data.remote.repository.ComicDetailRepositoryImpl
import com.fzellner.marvelappcompose.comicdetails.domain.repository.ComicDetailRepository
import com.fzellner.marvelappcompose.comicdetails.domain.usecase.GetComicDetailUseCase
import com.fzellner.marvelappcompose.comicdetails.domain.usecase.GetComicDetailUseCaseImpl
import com.fzellner.marvelappcompose.comicdetails.presentation.mapper.ComicDetailEntityToUiModelMapper
import com.fzellner.marvelappcompose.network.module.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ComicDetailModule {

    @Singleton
    @Provides
    fun provideComicDetailApi(retrofit: Retrofit): ComicDetailApi {
       return retrofit.create(ComicDetailApi::class.java)
    }

    @Singleton
    @Provides
    fun proviceComicDetailResponseToEntityMapper() =
        ComicDetailResponseToEntityMapper()

    @Singleton
    @Provides
    fun provideComicDetailRepository(
        comicDetailRepository: ComicDetailApi,
        mapper: ComicDetailResponseToEntityMapper
    ): ComicDetailRepository {
        return ComicDetailRepositoryImpl(
            comicDetailRepository,
            mapper
        )
    }

    @Singleton
    @Provides
    fun providesComicDetailUseCase(
        repository: ComicDetailRepository
    ): GetComicDetailUseCase {
        return GetComicDetailUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun providesComicDetailEntityMapperToUiModel() = ComicDetailEntityToUiModelMapper()

}