import com.example.androidbase.presentation.BuildModules.DATA
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import com.example.androidbase.presentation.Dependencies.ANDROIDX_APPCOMPAT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CONSTRAINT_LAYOUT
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
import com.example.androidbase.presentation.Dependencies.FRAGMENT_KTS
import com.example.androidbase.presentation.Dependencies.GLIDE
import com.example.androidbase.presentation.Dependencies.GLIDE_COMPILER
import com.example.androidbase.presentation.Dependencies.GOOGLE_GSON
import com.example.androidbase.presentation.Dependencies.JUNIT
import com.example.androidbase.presentation.Dependencies.LIVE_DATA
import com.example.androidbase.presentation.Dependencies.NAVIGATION_FRAGMENT
import com.example.androidbase.presentation.Dependencies.NAVIGATION_UI
import com.example.androidbase.presentation.Dependencies.RETROFIT
import com.example.androidbase.presentation.Dependencies.RETROFIT_CONVERTER_GSON
import com.example.androidbase.presentation.Dependencies.RETROFIT_INTERCEPTOR
import com.example.androidbase.presentation.Dependencies.ROOM_COMPILER
import com.example.androidbase.presentation.Dependencies.ROOM_KTX
import com.example.androidbase.presentation.Dependencies.ROOM_RUNTIME
import com.example.androidbase.presentation.Dependencies.TEST_EXPRESO
import com.example.androidbase.presentation.Dependencies.TEST_JUNIT
import com.example.androidbase.presentation.Dependencies.VIEW_MODEL

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
    implementation(NAVIGATION_FRAGMENT)
    implementation(NAVIGATION_UI)
    //Room Dependecies
    implementation(ROOM_RUNTIME)
    implementation(ROOM_KTX)
    kapt(ROOM_COMPILER)
    //Retrofit Dependecies
    implementation(RETROFIT)
    implementation(RETROFIT_CONVERTER_GSON)
    implementation(RETROFIT_INTERCEPTOR)
    //GSON
    implementation(GOOGLE_GSON)
    // ViewModel
    implementation(VIEW_MODEL)
    // LiveData
    implementation(LIVE_DATA)
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
    implementation(GLIDE)
    annotationProcessor(GLIDE_COMPILER)
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
}