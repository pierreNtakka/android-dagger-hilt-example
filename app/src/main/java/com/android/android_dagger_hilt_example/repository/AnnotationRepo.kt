package com.android.android_dagger_hilt_example.repository

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClearRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CipherRepository