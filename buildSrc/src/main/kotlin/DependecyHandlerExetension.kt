package com.example.androidbase.presentation

import org.gradle.api.artifacts.dsl.DependencyHandler


private const val IMPLEMENTATION = "implementation"

fun DependencyHandler.implementation(dependency: String) {
    add(IMPLEMENTATION, dependency)
}


