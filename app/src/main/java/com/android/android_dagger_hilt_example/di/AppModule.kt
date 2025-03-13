package com.android.android_dagger_hilt_example.di

import android.content.Context
import com.android.android_dagger_hilt_example.AndroidHiltExampleApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val STRING1 = "string1"
    const val STRING2 = "string2"

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): AndroidHiltExampleApplication {
        return app as AndroidHiltExampleApplication
    }

    @Singleton
    @Provides
    @Named(STRING1)
    fun provideExampleString(): String {
        return "Hello Hilt!! "
    }

    @Singleton
    @Provides
    @Named(STRING2)
    fun provideExampleString2(): String {
        return "Hello Hilt2!! "
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}