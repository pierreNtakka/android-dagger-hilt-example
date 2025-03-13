package com.android.android_dagger_hilt_example.network

import com.android.android_dagger_hilt_example.BuildConfig
import com.android.android_dagger_hilt_example.network.interceptor.EncryptionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @ClearOkHttpClient
    fun provideClearOkhttpClientApi(): OkHttpClient {
        return provideSimpleOkhttpClientBuilder().build()
    }

    @Singleton
    @Provides
    fun provideAesCipherDecipherInterceptor(): Interceptor {
        return EncryptionInterceptor()
    }

    @Singleton
    @Provides
    @CipherOkHttpClient
    fun provideCipherOkhttpClient(
        cipherInterceptor: EncryptionInterceptor
    ): OkHttpClient {
        return provideSimpleOkhttpClientBuilder().addInterceptor(cipherInterceptor).build()
    }

    @Provides
    @Singleton
    @ClearJsonApi
    fun provideClearJsonApiPlaceholder(@ClearOkHttpClient okHttpClient: OkHttpClient): JsonPlaceholderApi {
        return createJsonPlaceholderApi(okHttpClient)
    }

    @Provides
    @Singleton
    @CipherJsonApi
    fun provideClearJsonApiPlaceholderEncypher(@CipherOkHttpClient okHttpClient: OkHttpClient): JsonPlaceholderApi {
        return createJsonPlaceholderApi(okHttpClient)
    }

    private fun createJsonPlaceholderApi(okHttpClient: OkHttpClient): JsonPlaceholderApi {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json;".toMediaType()))
            .baseUrl(BuildConfig.JSONPLACEHOLDER_API_URL)
            .client(okHttpClient)
            .build()
            .create(JsonPlaceholderApi::class.java)
    }

    private fun provideSimpleOkhttpClientBuilder(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            else okhttp3.logging.HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(JsonPlaceholderApiConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(JsonPlaceholderApiConstant.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(JsonPlaceholderApiConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
    }

}