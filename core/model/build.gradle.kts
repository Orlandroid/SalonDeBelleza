import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION

plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.model"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = MIN_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(libs.androidCoreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.testJunit)
}