import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.loyalty"
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
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(platform(libs.firebaseBom))
    implementation(libs.bundles.firebase)
    implementation(libs.androidCoreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.testJunit)
    implementation(libs.bundles.composeUi)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeMaterial)
    ksp(libs.hiltAndroidCompiler)
    ksp(libs.androidxHiltCompiler)
}