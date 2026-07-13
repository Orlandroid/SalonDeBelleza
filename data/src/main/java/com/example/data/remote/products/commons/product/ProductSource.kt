package com.example.data.remote.products.commons.product

enum class ProductSource(val supportsCategories: Boolean) {
    DUMMY_JSON(false),
    FAKE_STORE(true),
    PLATZI(true),
    MY_DUMMY_API(false)
}