package com.android.android_dagger_hilt_example.data.di

import android.content.Context
import com.android.android_dagger_hilt_example.data.repository.CipherRepository
import com.android.android_dagger_hilt_example.data.repository.ClearRepository
import com.android.android_dagger_hilt_example.data.repository.ImageRepository
import com.android.android_dagger_hilt_example.data.repository.ImageRepositoryImpl
import com.android.android_dagger_hilt_example.data.repository.JsonPlaceholderRepository
import com.android.android_dagger_hilt_example.data.repository.JsonPlaceholderRepositoryImpl
import com.android.android_dagger_hilt_example.network.CipherJsonApi
import com.android.android_dagger_hilt_example.network.ClearJsonApi
import com.android.android_dagger_hilt_example.network.JsonPlaceholderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    @ClearRepository
    fun provideJsonPlaceholderRepo(
        @ClearJsonApi jsonPlaceholderApi: JsonPlaceholderApi
    ): JsonPlaceholderRepository {
        return JsonPlaceholderRepositoryImpl(jsonPlaceholderApi = jsonPlaceholderApi)
    }


    @Singleton
    @Provides
    @CipherRepository
    fun provideJsonPlaceholderCipherRepo(
        @CipherJsonApi jsonPlaceholderApi: JsonPlaceholderApi
    ): JsonPlaceholderRepository {
        return JsonPlaceholderRepositoryImpl(jsonPlaceholderApi = jsonPlaceholderApi)
    }

    @Singleton
    @Provides
    fun provideCamera(
        context: Context
    ): ImageRepository {
        return ImageRepositoryImpl(context = context)
    }


}