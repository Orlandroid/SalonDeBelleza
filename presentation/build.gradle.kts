import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.kotlin.serialization)

}

android {
    compileSdk = COMPILE_SDK_VERSION
    buildToolsVersion = "30.0.3"

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.citassalon"
        minSdk = MIN_SDK_VERSION
        targetSdk = TARGET_SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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
    namespace = "com.example.citassalon"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":di"))
    implementation(project(":auth"))
    implementation(libs.androidCoreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.testJunit)
    androidTestImplementation(libs.espressoCore)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.bundles.retrofit)
    implementation(libs.gson)
    implementation(libs.bundles.daggerHilt)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.androidxHiltCompiler)
    implementation(platform(libs.firebaseBom))
    implementation(libs.bundles.firebase)
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    implementation(libs.kotlinStdlib)
    implementation(libs.androidxWorkRuntimeKtx)
    implementation(libs.androidxStartupRuntime)

    //Compose
    implementation(platform(libs.composeBom))
    implementation(libs.kotlinSerializationJson)


    implementation(libs.bundles.composeMaterial)
    implementation(libs.bundles.composeUi)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.baseTesting)
}