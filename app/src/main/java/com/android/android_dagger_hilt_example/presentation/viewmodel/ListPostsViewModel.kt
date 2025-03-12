package com.android.android_dagger_hilt_example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.network.Resource
import com.android.android_dagger_hilt_example.presentation.uses_case.CreatePostUseCase
import com.android.android_dagger_hilt_example.presentation.uses_case.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPostsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val createPostUseCase: CreatePostUseCase,
) : ViewModel() {

    private val _dataFlow = MutableStateFlow<ListPostsState>(ListPostsState.Loading)
    val dataFlow = _dataFlow.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            getPostUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _dataFlow.value =
                            ListPostsState.Success(list = resource.data ?: emptyList())
                    }

                    is Resource.Loading -> {
                        _dataFlow.value = ListPostsState.Loading
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
    }

    fun createPost() {
        viewModelScope.launch {
            createPostUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        fetchData()
                    }

                    is Resource.Loading -> {
                        _dataFlow.value = ListPostsState.Loading
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
    }


    sealed class ListPostsState {
        class Success(val list: List<Post>) : ListPostsState()
        data object Loading : ListPostsState()
        data object Error : ListPostsState()
    }
}