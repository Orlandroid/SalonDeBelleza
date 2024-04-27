import com.example.androidbase.presentation.BuildModules.DATA
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import com.example.androidbase.presentation.Dependencies.ANDROIDX_APPCOMPAT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CONSTRAINT_LAYOUT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CORE_KTX
import com.example.androidbase.presentation.Dependencies.ANDROID_MATERIAL
import com.example.androidbase.presentation.Dependencies.FIREBASE_BOM
import com.example.androidbase.presentation.Dependencies.FRAGMENT_KTS
import com.example.androidbase.presentation.Dependencies.GOOGLE_GSON
import com.example.androidbase.presentation.Dependencies.JUNIT
import com.example.androidbase.presentation.Dependencies.LIVE_DATA
import com.example.androidbase.presentation.Dependencies.TEST_EXPRESO
import com.example.androidbase.presentation.Dependencies.TEST_JUNIT
import com.example.androidbase.presentation.Dependencies.VIEW_MODEL
import com.example.androidbase.presentation.Dependencies.daggerHilt
import com.example.androidbase.presentation.Dependencies.firebase
import com.example.androidbase.presentation.Dependencies.glide
import com.example.androidbase.presentation.Dependencies.navigationComponent
import com.example.androidbase.presentation.Dependencies.retrofit
import com.example.androidbase.presentation.Dependencies.room

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    compileSdk = COMPILE_SDK_VERSION
    buildToolsVersion = "30.0.3"

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
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
    implementation(project(DATA))
    implementation(project(DOMAIN))
    implementation(ANDROIDX_CORE_KTX)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROID_MATERIAL)
    implementation(ANDROIDX_CONSTRAINT_LAYOUT)
    testImplementation(JUNIT)
    androidTestImplementation(TEST_JUNIT)
    androidTestImplementation(TEST_EXPRESO)
    //Navigation component
    navigationComponent()
    //Room Dependecies
    room()
    //Retrofit Dependecies
    retrofit()
    //GSON
    implementation(GOOGLE_GSON)
    // ViewModel
    implementation(VIEW_MODEL)
    // LiveData
    implementation(LIVE_DATA)
    //Dagger - Hilt
    daggerHilt()
    //Firebases
    implementation(platform(FIREBASE_BOM))
    firebase()

    glide()
    implementation(FRAGMENT_KTS)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    //Shimmer
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.github.ome450901:SimpleRatingBar:1.5.1")
    //Rounded ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    //Skeleton
    implementation("com.faltenreich:skeletonlayout:5.0.0")
    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")
    // Kotlin + coroutines(WorkManager)
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.startup:startup-runtime:1.1.1")

    //Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.github.a914-gowtham:compose-ratingbar:1.3.4")
    implementation("androidx.compose.ui:ui-tooling:1.6.6")

}