package com.example.domain.entities.products

data class Category(
    val id: String,
    val name: String,
    val image: String? = null,
    val slug: String? = null
)