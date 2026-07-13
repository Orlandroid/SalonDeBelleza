package com.example.domain.entities.remote.products

data class Category(
    val id: String,
    val name: String,
    val image: String? = null,
    val slug: String? = null
)