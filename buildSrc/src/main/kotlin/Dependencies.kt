package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val ROOM_VERSION = "2.6.1"
}

object Dependencies {
    const val DAGGER_HILT = "com.google.dagger:hilt-android:2.49"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:2.49"
    const val ANDROID_HILT_COMPILER = "androidx.hilt:hilt-compiler:1.0.0"
    const val ANDROID_HILT_WORK = "androidx.hilt:hilt-work:1.0.0"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:31.2.0"
    const val FIREBASE_AUTH_KTX = "com.google.firebase:firebase-auth-ktx"
    const val FIREBASE_AUTH_V_KTX = "com.google.firebase:firebase-auth-ktx:21.1.0"
    const val FIREBASE_ANALYTICS_KTX = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_PLAY_SERVICES = "com.google.android.gms:play-services-auth:20.5.0"
    const val FIREBASE_DATABASE = "com.google.firebase:firebase-database-ktx"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM_VERSION}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM_VERSION}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM_VERSION}"

    fun DependencyHandler.room() {
        implementation(ROOM_KTX)
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
    }

    fun DependencyHandler.daggerHilt() {
        implementation(DAGGER_HILT)
        implementation(ANDROID_HILT_WORK)
        kapt(ANDROID_HILT_COMPILER)
        kapt(DAGGER_HILT_COMPILER)
    }

    fun DependencyHandler.firebase() {
        implementation(FIREBASE_AUTH_KTX)
        implementation(FIREBASE_AUTH_V_KTX)
        implementation(FIREBASE_ANALYTICS_KTX)
        implementation(FIREBASE_PLAY_SERVICES)
        implementation(FIREBASE_DATABASE)
    }
}