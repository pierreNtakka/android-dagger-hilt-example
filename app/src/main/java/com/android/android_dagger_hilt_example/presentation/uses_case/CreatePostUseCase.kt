package com.android.android_dagger_hilt_example.presentation.uses_case

import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.network.Resource
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderEncypherRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class CreatePostUseCase @Inject constructor(private val jsonPlaceholderRepository: JsonPlaceholderEncypherRepository) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val response =
                jsonPlaceholderRepository.createPost(
                    Post(
                        body = "ciao post",
                        title = "Titolo",
                        userId = 1
                    )
                )
            emit(Resource.Success(data = response))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> emit(Resource.ConnectionError(message = throwable.message))
                is HttpException -> {
                    emit(Resource.Error(code = throwable.code(), message = throwable.message()))
                }

                else -> {
                    emit(Resource.Error())
                }
            }
        }
    }

}