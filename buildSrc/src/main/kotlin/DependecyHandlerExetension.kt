package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler


private const val IMPLEMENTATION = "implementation"
private const val KAPT = "kapt"

fun DependencyHandler.implementation(dependency: String) {
    add(IMPLEMENTATION, dependency)
}


fun DependencyHandler.kapt(dependency: String) {
    add(KAPT, dependency)
}

