package com.imageviewer.demo.core.di

import android.content.Context
import com.imageviewer.demo.core.util.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideValidator(
        @ApplicationContext context: Context
    ): Validator {
        return Validator(context)
    }
}