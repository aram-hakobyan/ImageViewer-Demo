package com.pixabay.demo.data.di

import com.pixabay.demo.data.api.PixabayApi
import com.pixabay.demo.data.repo.ImageRepositoryImpl
import com.pixabay.demo.data.repo.LoginRepositoryImpl
import com.pixabay.demo.data.repo.RegisterRepositoryImpl
import com.pixabay.demo.data.repo.TokenRepositoryImpl
import com.pixabay.demo.domain.repo.ImageRepository
import com.pixabay.demo.domain.repo.LoginRepository
import com.pixabay.demo.domain.repo.RegisterRepository
import com.pixabay.demo.domain.repo.TokenRepository
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
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideTokenRepository(): TokenRepository {
        return TokenRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        pixabayApi: PixabayApi,
        tokenRepository: TokenRepository
    ): ImageRepository {
        return ImageRepositoryImpl(
            pixabayApi,
            tokenRepository
        )
    }
}