package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler


private const val IMPLEMENTATION = "implementation"
private const val KAPT = "kapt"
private const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
private const val TEST_IMPLEMENTATION = "testImplementation"
private const val ANNOTATION_PROCESSOR = "annotationProcessor"

fun DependencyHandler.implementation(dependecy: String) {
    add(IMPLEMENTATION, dependecy)
}


fun DependencyHandler.kapt(dependecy: String) {
    add(KAPT, dependecy)
}

fun DependencyHandler.androidTestImplementation(dependecy: String) {
    add(ANDROID_TEST_IMPLEMENTATION, dependecy)
}

fun DependencyHandler.testImplementation(dependecy: String) {
    add(TEST_IMPLEMENTATION, dependecy)
}

fun DependencyHandler.annotationProcessor(dependecy: String) {
    add(ANNOTATION_PROCESSOR, dependecy)
}

