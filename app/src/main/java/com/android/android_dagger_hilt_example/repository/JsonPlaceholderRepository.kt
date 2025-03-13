package com.android.android_dagger_hilt_example.repository

import com.android.android_dagger_hilt_example.model.Post

interface JsonPlaceholderRepository {

    suspend fun getPosts(): List<Post>

    suspend fun createPost(post: Post): Post

}