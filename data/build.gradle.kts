import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.implementation

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
    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)
    implementation(libs.bundles.daggerHilt)
    kapt(libs.hiltAndroidCompiler)
    kapt(libs.androidxHiltCompiler)
    implementation(platform(libs.firebaseBom))
    implementation(libs.bundles.firebase)
    implementation(libs.kotlinSerialization)
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
}