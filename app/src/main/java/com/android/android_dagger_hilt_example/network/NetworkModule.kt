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
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideSimpleOkhttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(JsonPlaceholderApiConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(JsonPlaceholderApiConstant.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(JsonPlaceholderApiConstant.WRITE_TIMEOUT, TimeUnit.SECONDS)
    }


    @Singleton
    @Provides
    @ClearOkHttpClient
    fun provideClearOkhttpClientApi(
        okhttpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return okhttpClientBuilder.build()
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
        okhttpClientBuilder: OkHttpClient.Builder, cipherInterceptor: EncryptionInterceptor
    ): OkHttpClient {
        return okhttpClientBuilder.addInterceptor(cipherInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json;".toMediaType()))
            .baseUrl(BuildConfig.JSONPLACEHOLDER_API_URL)
    }


    @Provides
    @Singleton
    @ClearJsonAPi
    fun provideClearJsonApiPlaceholder(
        retrofitBuilder: Retrofit.Builder, @ClearOkHttpClient okHttpClient: OkHttpClient
    ): JsonPlaceholderApi {
        return createJsonPlaceholderApi(retrofitBuilder, okHttpClient)
    }

    @Provides
    @Singleton
    @CipherJsonAPi
    fun provideClearJsonApiPlaceholderEncypher(
        retrofitBuilder: Retrofit.Builder, @CipherOkHttpClient okHttpClient: OkHttpClient
    ): JsonPlaceholderApi {
        return createJsonPlaceholderApi(retrofitBuilder, okHttpClient)
    }

    private fun createJsonPlaceholderApi(
        retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient
    ): JsonPlaceholderApi {
        return retrofitBuilder.client(okHttpClient).build().create(JsonPlaceholderApi::class.java)
    }


}