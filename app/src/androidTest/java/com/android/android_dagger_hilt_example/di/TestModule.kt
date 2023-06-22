package com.android.android_dagger_hilt_example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestModule {

    @Singleton
    @Provides
    @Named(AppModule.STRING1)
    fun provideExampleString(): String {
        return "Hello Test Hilt!!"
    }

    @Singleton
    @Provides
    @Named(AppModule.STRING2)
    fun provideExampleString2(): String {
        return "Hello Test Hilt2!!"
    }
}