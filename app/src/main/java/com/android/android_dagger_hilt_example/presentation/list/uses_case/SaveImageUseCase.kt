package com.android.android_dagger_hilt_example.presentation.list.uses_case

import android.net.Uri
import com.android.android_dagger_hilt_example.data.repository.ImageRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    fun execute(fileName: String): Uri? {
        return imageRepository.saveImage(fileName = fileName)
    }
}