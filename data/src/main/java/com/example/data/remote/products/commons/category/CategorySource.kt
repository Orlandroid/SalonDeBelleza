package com.example.data.remote.products.commons.category

import com.example.data.remote.products.commons.product.ProductSource

enum class CategorySource {
    FAKE_STORE,
    PLATZI,
}

fun CategorySource.toProductSource(): ProductSource {
    return when (this) {
        CategorySource.FAKE_STORE -> {
            ProductSource.FAKE_STORE
        }

        CategorySource.PLATZI -> {
            ProductSource.PLATZI
        }
    }
}