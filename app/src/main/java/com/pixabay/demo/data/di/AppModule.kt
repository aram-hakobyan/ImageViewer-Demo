package com.pixabay.demo.data.di

import com.pixabay.demo.data.api.PhotosApi
import com.pixabay.demo.data.repo.PhotosRepositoryImpl
import com.pixabay.demo.domain.repo.PhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePhotosRepository(
        photosApi: PhotosApi
    ): PhotosRepository {
        return PhotosRepositoryImpl(
            photosApi
        )
    }
}