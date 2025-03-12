package com.android.android_dagger_hilt_example.network

import com.android.android_dagger_hilt_example.BuildConfig
import com.android.android_dagger_hilt_example.network.interceptor.EncryptionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class BaseOkHttpClient(
    private val connectionTimeoutSec: Long,
    private val readTimeoutSec: Long,
    private val writeTimeoutSec: Long,
) {


    fun getClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(connectionTimeoutSec, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSec, TimeUnit.SECONDS).build()
    }

    fun getSecureClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(EncryptionInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(connectionTimeoutSec, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSec, TimeUnit.SECONDS).build()
    }
}


sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = 0) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String? = null, code: Int? = null) :
        Resource<T>(data, message, code)

    class ConnectionError<T>(data: T? = null, message: String? = null) :
        Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}