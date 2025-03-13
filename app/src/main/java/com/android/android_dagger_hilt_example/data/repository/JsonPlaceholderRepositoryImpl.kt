package com.android.android_dagger_hilt_example.data.repository

import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.network.JsonPlaceholderApi
import javax.inject.Inject

class JsonPlaceholderRepositoryImpl @Inject constructor(
    private val jsonPlaceholderApi: JsonPlaceholderApi,
) : JsonPlaceholderRepository {

    override suspend fun getPosts() = jsonPlaceholderApi.getPosts()

    override suspend fun createPost(post: Post): Post {
        return jsonPlaceholderApi.createPost(post = post)
    }
}
