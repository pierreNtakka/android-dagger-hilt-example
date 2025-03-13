package com.android.android_dagger_hilt_example.network

import com.android.android_dagger_hilt_example.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object JsonPlaceholderApiConstant {
    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 120L
}


interface JsonPlaceholderApi {

    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @POST("/posts")
    suspend fun createPost(@Body post: Post): Post
}
