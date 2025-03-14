package com.android.android_dagger_hilt_example.presentation.list.uses_case

import com.android.android_dagger_hilt_example.data.repository.ClearRepository
import com.android.android_dagger_hilt_example.data.repository.JsonPlaceholderRepository
import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.network.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CreatePostUseCase @Inject constructor(@ClearRepository private val jsonPlaceholderRepository: JsonPlaceholderRepository) {

    suspend operator fun invoke(post: Post): Resource<Post> {
        try {
            val response =
                jsonPlaceholderRepository.createPost(post = post)
            return Resource.Success(data = response)
        } catch (throwable: HttpException) {
            return Resource.Error(code = throwable.code(), message = throwable.message())
        } catch (ex: IOException) {
            return Resource.ConnectionError(message = ex.message ?: "")
        } catch (ex: Exception) {
            return Resource.Error(message = ex.message ?: "")
        }
    }
}