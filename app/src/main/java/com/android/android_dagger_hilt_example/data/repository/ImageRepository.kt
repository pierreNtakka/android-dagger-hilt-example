package com.android.android_dagger_hilt_example.data.repository

import android.net.Uri

interface ImageRepository {

    fun saveImage(fileName: String): Uri?

}