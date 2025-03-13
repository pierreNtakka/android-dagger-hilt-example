package com.android.android_dagger_hilt_example.network


sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: Int? = 0) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String? = null, code: Int? = null) :
        Resource<T>(data, message, code)

    class ConnectionError<T>(data: T? = null, message: String? = null) :
        Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}