import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization")
}

android {
    compileSdk = COMPILE_SDK_VERSION
    buildToolsVersion = "30.0.3"

    buildFeatures {
        compose = true
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
    namespace = "com.example.citassalon"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidCoreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.testJunit)
    androidTestImplementation(libs.espressoCore)
    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)
    implementation(libs.bundles.retrofit)
    implementation(libs.gson)
    implementation(libs.bundles.daggerHilt)
    kapt(libs.hiltAndroidCompiler)
    kapt(libs.androidxHiltCompiler)
    implementation(platform(libs.firebaseBom))
    implementation(libs.bundles.firebase)
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    implementation(libs.kotlinStdlib)
    implementation(libs.androidxWorkRuntimeKtx)
    implementation(libs.androidxStartupRuntime)

    //Compose
    implementation("androidx.compose.runtime:runtime")
    implementation(platform(libs.composeBom))
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")


    implementation(libs.composeUi)
    implementation(libs.bundles.composeMaterial)
    implementation(libs.constraintlayoutCompose)
    implementation(libs.lottieCompose)
    implementation(libs.coilCompose)
    implementation(libs.composeRuntimeLivedata)
    implementation(libs.lifecycleViewmodelCompose)
    implementation(libs.composeRatingbar)
    implementation(libs.mapsCompose)
    implementation(libs.navigationCompose)
    implementation(libs.hiltNavigationCompose)
    implementation(libs.composeUiTooling)
    implementation(libs.activityCompose)
    testImplementation(libs.mockk)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}