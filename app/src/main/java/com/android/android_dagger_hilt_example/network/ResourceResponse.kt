package com.android.android_dagger_hilt_example.network


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val code: Int? = null) : Resource<Nothing>()
    data class ConnectionError(val message: String) : Resource<Nothing>()
}