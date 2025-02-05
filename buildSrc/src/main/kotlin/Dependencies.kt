package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val ANDROID_MATERIAL_VERSION = "1.9.0"
    const val ANDROIDX_ACTIVITY = "1.2.1"
    const val ANDROIDX_APPCOMPAT_VERSION = "1.6.1"
    const val ANDROIDX_CONSTRAINT_LAYOUT_VERSION = "2.0.4"
    const val ANDROIDX_CORE_KTX_VERSION = "1.8.0"
    const val ANDROIDX_FRAGMENT = "1.3.1"
    const val ANDROIDX_LIFECYCLE_VERSION = "2.2.0"
    const val CARDVIEW_VERSION = "1.0.0"
    const val DRAWER_VERSION = "1.1.1"
    const val DAGGER_VERSION_ANDROID = "2.45"
    const val DAGGER_VERSION_COMPILER = "2.45"
    const val GLIDE_VERSION = "4.14.2"
    const val KOIN_VERSION = "2.2.0"
    const val KOTLIN_VERSION = "1.9.22"
    const val KOTLINX_COROUTINES_VERSION = "1.3.9"
    const val MOSHI_VERSION = "1.11.0"
    const val OKHTTP_VERSION = "4.0.1"
    const val PAGING_VERSION = "3.0.0-beta02"
    const val RECYCLERVIEW_VERSION = "1.1.0"
    const val RETROFIT_VERSION = "2.9.0"
    const val ROOM_VERSION = "2.6.1"
    const val NAVIGATION_VERSION = "2.7.6"
    const val DAGGER_HILT_VERSION = "2.38.1"
    const val LIFECYCLE_VERSION = "2.3.1"
}

object Dependencies {
    const val ANDROID_MATERIAL = "com.google.android.material:material:1.9.0"
    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-ktx:1.2.1"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.6.1"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:1.8.0"
    const val ANDROIDX_FRAGMENT = "androidx.fragment:fragment-ktx:1.3.1"
    const val ANDROIDX_CORE_CORE_KTX = "androidx.core:core-ktx:1.7.0"
    const val CARDVIEW = "androidx.cardview:cardview:1.0.0"
    const val DRAWER_LAYOUT = "androidx.drawerlayout:drawerlayout:1.1.1"
    const val DAGGER_HILT = "com.google.dagger:hilt-android:2.49"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:2.49"
    const val ANDROID_HILT_COMPILER = "androidx.hilt:hilt-compiler:1.0.0"
    const val ANDROID_HILT_WORK = "androidx.hilt:hilt-work:1.0.0"
    const val GLIDE = "com.github.bumptech.glide:glide:4.14.2"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.14.2"
    const val VIEW_MODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"
    const val LIVE_DATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_VERSION}"
    const val FRAGMENT_KTS = "androidx.fragment:fragment-ktx:1.5.7"
    const val TEST_JUNIT = "androidx.test.ext:junit:1.1.4"
    const val JUNIT = "junit:junit:4.13.2"
    const val TEST_EXPRESO = "androidx.test.espresso:espresso-core:3.5.0"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:31.2.0"
    const val FIREBASE_AUTH_KTX = "com.google.firebase:firebase-auth-ktx"
    const val FIREBASE_AUTH_V_KTX = "com.google.firebase:firebase-auth-ktx:21.1.0"
    const val FIREBASE_ANALYTICS_KTX = "com.google.firebase:firebase-analytics-ktx"
    const val FIREBASE_PLAY_SERVICES = "com.google.android.gms:play-services-auth:20.5.0"
    const val FIREBASE_DATABASE = "com.google.firebase:firebase-database-ktx"
    const val GOOGLE_GSON = "com.google.code.gson:gson:2.9.0"
    const val ANDROIDX_LIFECYCLE =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}"
    const val ANDROIDX_LIFECYCLE_EXTENSIONS =
        "androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_VERSION}"
    const val KOIN = "org.koin:koin-core:${Versions.KOIN_VERSION}"
    const val KOIN_ANDROID = "org.koin:koin-android:${Versions.KOIN_VERSION}"
    const val KOIN_ANDROIDX_SCOPE = "org.koin:koin-androidx-scope:${Versions.KOIN_VERSION}"
    const val KOIN_ANDROIDX_VIEWMODEL = "org.koin:koin-androidx-viewmodel:${Versions.KOIN_VERSION}"
    const val KOTLIN_STDLIB_JDK7 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}"
    const val KOTLINX_COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES_VERSION}"
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}"
    const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}"
    const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI_VERSION}"
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_VERSION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_VERSION}"
    const val PAGING = "androidx.paging:paging-runtime:3.0.0-beta02"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.1.0"
    const val RETROFIT_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
    const val RETROFIT_CONVERTER_MOSHI =
        "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM_VERSION}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM_VERSION}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM_VERSION}"

    fun DependencyHandler.room() {
        implementation(ROOM_KTX)
        implementation(ROOM_RUNTIME)
        kapt(ROOM_COMPILER)
    }

    fun DependencyHandler.lifecycle() {
        implementation(VIEW_MODEL)
        implementation(LIVE_DATA)
        implementation(ANDROIDX_FRAGMENT)
    }

    fun DependencyHandler.retrofit() {
        implementation(RETROFIT)
        implementation(RETROFIT_CONVERTER_GSON)
        implementation(RETROFIT_CONVERTER_MOSHI)
        implementation(RETROFIT_INTERCEPTOR)
    }

    fun DependencyHandler.daggerHilt() {
        implementation(DAGGER_HILT)
        implementation(ANDROID_HILT_WORK)
        kapt(ANDROID_HILT_COMPILER)
        kapt(DAGGER_HILT_COMPILER)
    }

    fun DependencyHandler.glide() {
        implementation(GLIDE)
        annotationProcessor(GLIDE_COMPILER)
    }

    fun DependencyHandler.firebase() {
        implementation(FIREBASE_AUTH_KTX)
        implementation(FIREBASE_AUTH_V_KTX)
        implementation(FIREBASE_ANALYTICS_KTX)
        implementation(FIREBASE_PLAY_SERVICES)
        implementation(FIREBASE_DATABASE)
    }
}