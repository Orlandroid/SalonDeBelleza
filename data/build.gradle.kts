import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.data"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        testOptions {
            targetSdk = TARGET_SDK_VERSION
        }

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
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":di"))
    implementation(libs.androidCoreKtx)
    testImplementation(libs.junit4)
    testImplementation(libs.appcompat)
    androidTestImplementation(libs.testJunit)
    androidTestImplementation(libs.espressoCore)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.bundles.daggerHilt)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.androidxHiltCompiler)
    implementation(platform(libs.firebaseBom))
    implementation(libs.bundles.firebase)
    implementation(libs.kotlinSerializationJson)
    implementation(libs.preferencesDataStore)
}