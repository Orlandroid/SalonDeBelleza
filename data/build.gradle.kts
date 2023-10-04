import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.Dependencies.ANDROIDX_APPCOMPAT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CORE_KTX
import com.example.androidbase.presentation.Dependencies.ANDROID_HILT_COMPILER
import com.example.androidbase.presentation.Dependencies.ANDROID_HILT_WORK
import com.example.androidbase.presentation.Dependencies.ANDROID_MATERIAL
import com.example.androidbase.presentation.Dependencies.DAGGER_HILT
import com.example.androidbase.presentation.Dependencies.DAGGER_HILT_COMPILER
import com.example.androidbase.presentation.Dependencies.FIREBASE_ANALYTICS_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_AUTH_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_AUTH_V_KTX
import com.example.androidbase.presentation.Dependencies.FIREBASE_BOM
import com.example.androidbase.presentation.Dependencies.FIREBASE_DATABASE
import com.example.androidbase.presentation.Dependencies.FIREBASE_PLAY_SERVICES
import com.example.androidbase.presentation.Dependencies.JUNIT
import com.example.androidbase.presentation.Dependencies.RETROFIT
import com.example.androidbase.presentation.Dependencies.RETROFIT_CONVERTER_GSON
import com.example.androidbase.presentation.Dependencies.RETROFIT_INTERCEPTOR
import com.example.androidbase.presentation.Dependencies.ROOM_COMPILER
import com.example.androidbase.presentation.Dependencies.ROOM_KTX
import com.example.androidbase.presentation.Dependencies.ROOM_RUNTIME
import com.example.androidbase.presentation.Dependencies.TEST_EXPRESO
import com.example.androidbase.presentation.Dependencies.TEST_JUNIT

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
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

    implementation(project(DOMAIN))
    implementation(ANDROIDX_CORE_KTX)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROID_MATERIAL)
    testImplementation(JUNIT)
    androidTestImplementation(TEST_JUNIT)
    androidTestImplementation(TEST_EXPRESO)
    //Retrofit Dependecies
    implementation(RETROFIT)
    implementation(RETROFIT_CONVERTER_GSON)
    implementation(RETROFIT_INTERCEPTOR)
    //Room Dependecies
    implementation(ROOM_RUNTIME)
    implementation(ROOM_KTX)
    kapt(ROOM_COMPILER)
    //Dagger - Hilt
    implementation(DAGGER_HILT)
    kapt(DAGGER_HILT_COMPILER)
    kapt(ANDROID_HILT_COMPILER)
    implementation(ANDROID_HILT_WORK)
    //Firebases
    implementation(platform(FIREBASE_BOM))
    implementation(FIREBASE_AUTH_KTX)
    implementation(FIREBASE_AUTH_V_KTX)
    implementation(FIREBASE_ANALYTICS_KTX)
    implementation(FIREBASE_PLAY_SERVICES)
    implementation(FIREBASE_DATABASE)
}