package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler


private const val IMPLEMENTATION = "implementation"
private const val KAPT = "kapt"
private const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
private const val TEST_IMPLEMENTATION = "testImplementation"
private const val ANNOTATION_PROCESSOR = "annotationProcessor"

fun DependencyHandler.implementation(dependency: String) {
    add(IMPLEMENTATION, dependency)
}


fun DependencyHandler.kapt(dependency: String) {
    add(KAPT, dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: String) {
    add(ANDROID_TEST_IMPLEMENTATION, dependency)
}

fun DependencyHandler.testImplementation(dependency: String) {
    add(TEST_IMPLEMENTATION, dependency)
}

fun DependencyHandler.annotationProcessor(dependency: String) {
    add(ANNOTATION_PROCESSOR, dependency)
}

