package com.android.android_dagger_hilt_example.di

import com.android.android_dagger_hilt_example.repository.JsonPlaceholderEncypherRepository
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderEncypherRepositoryImpl
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderRepository
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Questo metodo si usa se si vuole avere più implementazioni dell'interfaccia
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDataFromInternetRepository(
        repositoryImpl: JsonPlaceholderRepositoryImpl,
    ): JsonPlaceholderRepository


    @Binds
    @Singleton
    abstract fun bindDataFromInternetRepository2(
        repositoryImpl2: JsonPlaceholderEncypherRepositoryImpl,
    ): JsonPlaceholderEncypherRepository

}