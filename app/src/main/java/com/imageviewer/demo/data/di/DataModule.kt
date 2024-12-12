package com.imageviewer.demo.data.di

import android.content.Context
import com.imageviewer.demo.data.api.PixabayApi
import com.imageviewer.demo.data.repository.ImageRepositoryImpl
import com.imageviewer.demo.data.repository.LoginRepositoryImpl
import com.imageviewer.demo.data.repository.RegisterRepositoryImpl
import com.imageviewer.demo.data.repository.TokenRepositoryImpl
import com.imageviewer.demo.data.repository.UserRepositoryImpl
import com.imageviewer.demo.domain.repository.ImageRepository
import com.imageviewer.demo.domain.repository.LoginRepository
import com.imageviewer.demo.domain.repository.RegisterRepository
import com.imageviewer.demo.domain.repository.TokenRepository
import com.imageviewer.demo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

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
    fun provideUserRepository(
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(context)
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