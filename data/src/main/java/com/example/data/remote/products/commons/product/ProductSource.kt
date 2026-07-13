package com.example.data.remote.products.commons.product

import com.example.data.remote.products.commons.category.CategorySource

enum class ProductSource(val supportsCategories: Boolean) {
    DUMMY_JSON(false),
    FAKE_STORE(true),
    PLATZI(true),
    MY_DUMMY_API(false)
}

fun ProductSource.toCategorySource(): CategorySource? {
    return when (this) {
        ProductSource.DUMMY_JSON -> {
            null
        }

        ProductSource.FAKE_STORE -> {
            CategorySource.FAKE_STORE
        }

        ProductSource.PLATZI -> {
            CategorySource.PLATZI
        }

        ProductSource.MY_DUMMY_API -> {
            null
        }
    }
}