package com.android.android_dagger_hilt_example.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.android_dagger_hilt_example.presentation.detail.fragment.DetailPostFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsPostViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {

    private val TAG = DetailsPostViewModel::class.java.simpleName


    fun getValue() {
        Log.d(
            TAG, "The value from savedStateHandle is '${
                DetailPostFragmentArgs.fromSavedStateHandle(savedStateHandle).post.title
            }'"
        )
    }
}