package com.example.model.state.models

enum class CategorySource {
    FAKE_STORE,
    PLATZI
}

fun CategorySource.toProductSource(): ProductSource =
    when (this) {
        CategorySource.FAKE_STORE -> {
            ProductSource.FAKE_STORE
        }

        CategorySource.PLATZI -> {
            ProductSource.PLATZI
        }
    }
