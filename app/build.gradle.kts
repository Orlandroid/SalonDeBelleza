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
    compileSdkVersion(31)
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.example.citassalon"
        minSdkVersion(22)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val room_version = "2.3.0"
    val retrofit_version = "2.9.0"
    val navigation_version = "2.3.5"
    val lifecycle_version = "2.3.1"
    val dagger_hilt_version = "2.38.1"
    val lottieVersion = "5.2.0"
    val work_version = "2.7.1"
    val kotlin_version = "1.5.21"


    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    //Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")
    //Room Dependecies
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    // nos servirá para usar corrutinas con room
    kapt("androidx.room:room-compiler:$room_version")
    //Retrofit Dependecies
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    //GSON
    implementation("com.google.code.gson:gson:2.9.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$dagger_hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-work:1.0.0")

    //Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.github.skydoves:androidveil:1.1.1")
    //Firebases
    implementation(platform("com.google.firebase:firebase-bom:31.2.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("com.google.android.gms:play-services-auth:20.4.1")
    implementation("com.google.firebase:firebase-database-ktx")
    //Rounded ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    //Skeleton
    implementation("com.faltenreich:skeletonlayout:4.0.0")
    //lottie
    implementation("com.airbnb.android:lottie:$lottieVersion")
    // Kotlin + coroutines(WorkManager)
    implementation("androidx.work:work-runtime-ktx:$work_version")
    implementation("androidx.startup:startup-runtime:1.1.1")
    //Image
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.github.ome450901:SimpleRatingBar:1.5.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")
}