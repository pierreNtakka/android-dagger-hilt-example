package com.android.android_dagger_hilt_example.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClearOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CipherOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClearJsonApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CipherJsonApi

