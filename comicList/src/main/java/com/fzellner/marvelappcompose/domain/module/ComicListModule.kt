package com.fzellner.marvelappcompose.domain.module

import com.fzellner.marvelappcompose.data.api.ComicListApi
import com.fzellner.marvelappcompose.data.remote.mapper.ComicListResponseToEntityMapper
import com.fzellner.marvelappcompose.data.remote.repository.GetComicListRepositoryImpl
import com.fzellner.marvelappcompose.domain.repository.GetComicListRepository
import com.fzellner.marvelappcompose.domain.usecase.GetComicListUseCase
import com.fzellner.marvelappcompose.domain.usecase.GetComicListUseCaseImpl
import com.fzellner.marvelappcompose.network.module.NetworkModule
import com.fzellner.marvelappcompose.presentation.mapper.ComicListEntityToUiModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ComicListModule {

    @Singleton
    @Provides
    fun provideComicListApi(retrofit: Retrofit): ComicListApi =
        retrofit.create(ComicListApi::class.java)

    @Singleton
    @Provides
    fun provideComicListRepository(
        comicListApi: ComicListApi,
        mapper: ComicListResponseToEntityMapper
    ): GetComicListRepository =
        GetComicListRepositoryImpl(api = comicListApi, mapper = mapper)

    @Singleton
    @Provides
    fun provideComicListUsecase(
        comicListRepository: GetComicListRepository
    ): GetComicListUseCase = GetComicListUseCaseImpl(comicListRepository)

    @Singleton
    @Provides
    fun providesComicListEntityToUiModelMapper() = ComicListEntityToUiModelMapper()

    @Singleton
    @Provides
    fun providesComicListResponseToEntityMapper() = ComicListResponseToEntityMapper()

}