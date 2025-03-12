package com.android.android_dagger_hilt_example.network.interceptor

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class EncryptionInterceptor : Interceptor {

    companion object {
        private val TAG: String = EncryptionInterceptor::class.java.simpleName
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private val alias = "ExampleAesKeyAlias"
        private val TRANSFORMATION = "AES/GCM/NoPadding"
    }

    private val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
        load(null)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalBody = originalRequest.body

        if (originalBody != null) {
            val cipherBody = encryptText(originalBody)

            val newRequest = originalRequest.newBuilder()
                .method(originalRequest.method, cipherBody)
                .build()

            return chain.proceed(newRequest)
        }

        return chain.proceed(originalRequest)
    }


    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry

        Log.d(TAG, "The key exist?: ${keyStore.containsAlias(alias)}")
        return existingKey?.secretKey ?: createKey()
    }

    private fun encryptText(body: RequestBody): RequestBody {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val buffer = okio.Buffer()
        body.writeTo(buffer)
        val byteArray = cipher.doFinal(buffer.readByteArray())
        val base64Encoded =
            Base64.encodeToString(byteArray, Base64.DEFAULT).toByteArray(StandardCharsets.UTF_8)
        return RequestBody.create(body.contentType(), base64Encoded)
    }

    private fun createKey(): SecretKey {
        val keyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )

        return keyGenerator.generateKey()
    }


}