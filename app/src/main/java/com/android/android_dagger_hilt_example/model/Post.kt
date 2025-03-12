package com.android.android_dagger_hilt_example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val body: String,
    val id: Int? = null,
    val title: String,
    val userId: Int,
) : Parcelable