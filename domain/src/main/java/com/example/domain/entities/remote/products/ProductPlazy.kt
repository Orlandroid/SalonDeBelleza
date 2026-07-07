package com.example.domain.entities.remote.products


data class ProductPlatzi(
    val id: Int,
    val title: String,
    val slug: String,
    val price: Int,
    val description: String,
    val category: Category,
    val images: List<String>,
    val creationAt: String,
    val updatedAt: String
)

data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val image: String,
    val creationAt: String,
    val updatedAt: String
)