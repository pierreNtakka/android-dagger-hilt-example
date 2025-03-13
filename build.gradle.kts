buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.navigation.safeargs) apply false
    alias(libs.plugins.navigation.safeargs.kotlin) apply false

    alias(libs.plugins.hilt) apply false

    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.serializzation) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.parcelize) apply false
}