// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        val kotlinVersion = "2.4.10"
        classpath("com.android.tools.build:gradle:9.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.5.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.60.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false
//    alias(libs.plugins.navigation.safe.gradle) apply false
//    alias(libs.plugins.hilt.android.gradle) apply false
//    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
//    alias(libs.plugins.android.library) apply false
//}