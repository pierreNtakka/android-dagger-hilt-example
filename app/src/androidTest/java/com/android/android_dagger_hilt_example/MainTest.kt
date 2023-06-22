package com.android.android_dagger_hilt_example

import com.android.android_dagger_hilt_example.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@UninstallModules(AppModule::class)
@HiltAndroidTest
class MainTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    @Named(AppModule.STRING2)
    lateinit var exampleString1: String

    @Inject
    @Named(AppModule.STRING2)
    lateinit var exampleString2: String

    @Test
    fun testExampleString1() {
        assertEquals("Hello Test Hilt!!", this.exampleString1)
    }

    @Test
    fun testExampleString2() {
        assertEquals("Hello Test Hilt2!!", this.exampleString2)
    }
}