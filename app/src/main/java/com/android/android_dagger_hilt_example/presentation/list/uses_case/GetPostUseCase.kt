package com.android.android_dagger_hilt_example.presentation.list.uses_case

import com.android.android_dagger_hilt_example.network.Resource
import com.android.android_dagger_hilt_example.repository.ClearRepository
import com.android.android_dagger_hilt_example.repository.JsonPlaceholderRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetPostUseCase @Inject constructor(@ClearRepository private val repository: JsonPlaceholderRepository) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getPosts()
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