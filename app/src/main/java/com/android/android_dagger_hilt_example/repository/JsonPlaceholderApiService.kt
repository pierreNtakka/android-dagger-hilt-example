package com.android.android_dagger_hilt_example.repository

import com.android.android_dagger_hilt_example.model.Post
import retrofit2.http.GET
import javax.inject.Inject

object JsonPlaceholderApiConstant {
    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 120L
}

interface JsonPlaceholderApi {
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}

interface JsonPlaceholderRepository {
    suspend fun getPosts(): List<Post>
}

class JsonPlaceholderRepositoryImpl @Inject constructor(
    private val jsonPlaceholderApi: JsonPlaceholderApi,
) : JsonPlaceholderRepository {

    override suspend fun getPosts() = jsonPlaceholderApi.getPosts()
}

