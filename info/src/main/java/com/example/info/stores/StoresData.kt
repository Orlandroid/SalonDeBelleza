package com.example.info.stores

import com.example.domain.ProductSource


const val FAKE_STORE = "Fake store"
const val DUMMY_JSON = "DummyJSON"
const val PLATZY = "Platzy"
const val MyDummy = "MyDummy"


data class Store(
    val name: String = "",
    val source: ProductSource
)