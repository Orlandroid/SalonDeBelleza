import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.wallet"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = MIN_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":core"))
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(libs.androidCoreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.testJunit)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.androidxHiltCompiler)
    implementation(libs.bundles.composeUi)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeMaterial)
}