// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        val kotlinVersion = "1.9.22"
        classpath("com.android.tools.build:gradle:8.8.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.4.2")
        val navVersion = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
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