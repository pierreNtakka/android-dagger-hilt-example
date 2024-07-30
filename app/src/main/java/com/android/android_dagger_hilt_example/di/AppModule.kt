package com.android.android_dagger_hilt_example.di

import android.content.Context
import com.android.android_dagger_hilt_example.AndroidHiltExampleApplication
import com.android.android_dagger_hilt_example.BuildConfig
import com.android.android_dagger_hilt_example.network.BaseOkHttpClient
import com.android.android_dagger_hilt_example.presentation.uses_case.GetPostUseCase
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderApi
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderApiConstant.BASE_URL
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderApiConstant.CONNECT_TIMEOUT
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderApiConstant.READ_TIMEOUT
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderApiConstant.WRITE_TIMEOUT
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderRepository
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideJsonPlaceHolderApi(gson: Gson): JsonPlaceholderApi {
        val baseHttpClient = BaseOkHttpClient(
            connectionTimeoutSec = CONNECT_TIMEOUT,
            readTimeoutSec = READ_TIMEOUT,
            writeTimeoutSec = WRITE_TIMEOUT
        ).getClient()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.JSONPLACEHOLDER_API_URL).client(baseHttpClient).build().create(JsonPlaceholderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGetPostUseCase(jsonPlaceholderRepository: JsonPlaceholderRepository): GetPostUseCase {
        return GetPostUseCase(jsonPlaceholderRepository)
    }

}