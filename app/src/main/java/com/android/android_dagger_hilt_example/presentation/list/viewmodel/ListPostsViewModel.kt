package com.android.android_dagger_hilt_example.presentation.list.viewmodel

import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.network.Resource
import com.android.android_dagger_hilt_example.presentation.list.uses_case.CreatePostUseCase
import com.android.android_dagger_hilt_example.presentation.list.uses_case.GetPostUseCase
import com.android.android_dagger_hilt_example.presentation.list.uses_case.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ListPostsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val saveImageUseCase: SaveImageUseCase
) : ViewModel() {

    private val _dataFlow = MutableStateFlow<ListPostsState>(ListPostsState.Loading)
    val dataFlow = _dataFlow.asStateFlow()

    private var currentImageUri: Uri? = null

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            when (val response = getPostUseCase()) {
                is Resource.Success -> {
                    _dataFlow.value =
                        ListPostsState.Success(list = response.data)
                }

                is Resource.ConnectionError -> {
                    _dataFlow.value = ListPostsState.Error
                }

                is Resource.Error -> {
                    _dataFlow.value = ListPostsState.Error
                }
            }
        }
    }

    fun createPost() {
        viewModelScope.launch {
            _dataFlow.value = ListPostsState.Loading

            val resource = createPostUseCase(
                Post(
                    body = "ciao post",
                    title = "Titolo",
                    userId = 1
                )
            )
            when (resource) {
                is Resource.Success -> {
                    fetchData()
                }

                is Resource.ConnectionError -> {
                    _dataFlow.value = ListPostsState.Error
                }

                is Resource.Error -> {
                    _dataFlow.value = ListPostsState.Error
                }
            }

        }
    }


    fun captureImage(launcher: ActivityResultLauncher<Uri>) {
        val filename =
            "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
        val uri = saveImageUseCase.execute(filename)

        if (uri != null) {
            currentImageUri = uri
            launcher.launch(uri)
        }
    }

    fun onPictureSelected(uri: Uri? = null) {
        if (uri != null) {
            Log.d(this::class::class.java.simpleName, uri.toString())
        } else {
            Log.d(this::class::class.java.simpleName, currentImageUri.toString())
        }

    }

    sealed class ListPostsState {
        class Success(val list: List<Post>) : ListPostsState()
        data object Loading : ListPostsState()
        data object Error : ListPostsState()
    }
}