plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.jetbrains.kotlin.plugin.serializzation)
    alias(libs.plugins.jetbrains.kotlin.plugin.parcelize)
}

android {
    namespace = "com.android.android_dagger_hilt_example"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.android.android_dagger_hilt_example"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "JSONPLACEHOLDER_API_URL",
            "\"https://jsonplaceholder.typicode.com\""
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }


    applicationVariants.all {
        this.outputs
            .map { it as com.android.build.gradle.internal.api.ApkVariantOutputImpl }
            .forEach { output ->
                val variant = this.buildType.name
                var apkName = "dagger_hilt_example" + this.versionName
                if (variant.isNotEmpty()) apkName += "_$variant"
                apkName += ".apk"
                println("ApkName=$apkName ${this.buildType.name}")
                output.outputFileName = apkName
            }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)



    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}