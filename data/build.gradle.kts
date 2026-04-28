import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.Dependencies.ANDROID_HILT_COMPILER
import com.example.androidbase.presentation.Dependencies.ANDROID_HILT_WORK
import com.example.androidbase.presentation.Dependencies.DAGGER_HILT
import com.example.androidbase.presentation.Dependencies.DAGGER_HILT_COMPILER
import com.example.androidbase.presentation.Dependencies.FIREBASE_ANALYTICS_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_AUTH_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_AUTH_V_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_DATABASE
import com.example.androidbase.presentation.Dependencies.FIREBASE_PLAY_SERVICES
import com.example.androidbase.presentation.Dependencies.ROOM_COMPILER
import com.example.androidbase.presentation.Dependencies.ROOM_KTX
import com.example.androidbase.presentation.Dependencies.ROOM_RUNTIME
import com.example.androidbase.presentation.implementation
import com.example.androidbase.presentation.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.example.data"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        targetSdk = TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

dependencies {

    implementation(project(":domain"))
    implementation(libs.androidCoreKtx)
    testImplementation(libs.junit4)
    testImplementation(libs.appcompat)
    androidTestImplementation(libs.testJunit)
    androidTestImplementation(libs.espressoCore)
    implementation(libs.bundles.retrofit)
    implementation(ROOM_KTX)
    implementation(ROOM_RUNTIME)
    kapt(ROOM_COMPILER)
    implementation(DAGGER_HILT)
    implementation(ANDROID_HILT_WORK)
    kapt(ANDROID_HILT_COMPILER)
    kapt(DAGGER_HILT_COMPILER)
    implementation(platform(libs.firebaseBom))
    implementation(FIREBASE_AUTH_KTX)
    implementation(FIREBASE_AUTH_V_KTX)
    implementation(FIREBASE_ANALYTICS_KTX)
    implementation(FIREBASE_PLAY_SERVICES)
    implementation(FIREBASE_DATABASE)
    implementation(libs.kotlinSerialization)
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
}