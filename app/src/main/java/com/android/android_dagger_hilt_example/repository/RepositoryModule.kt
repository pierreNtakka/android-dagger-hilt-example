package com.android.android_dagger_hilt_example.repository

import com.android.android_dagger_hilt_example.network.CipherJsonAPi
import com.android.android_dagger_hilt_example.network.ClearJsonAPi
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
        @ClearJsonAPi jsonPlaceholderApi: JsonPlaceholderApi
    ): JsonPlaceholderRepository {
        return JsonPlaceholderRepositoryImpl(jsonPlaceholderApi = jsonPlaceholderApi)
    }


    @Singleton
    @Provides
    @CipherRepository
    fun provideJsonPlaceholderCipherRepo(
        @CipherJsonAPi jsonPlaceholderApi: JsonPlaceholderApi
    ): JsonPlaceholderRepository {
        return JsonPlaceholderRepositoryImpl(jsonPlaceholderApi = jsonPlaceholderApi)
    }


}