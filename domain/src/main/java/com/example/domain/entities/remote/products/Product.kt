package com.example.domain.entities.remote.products

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val image: String
)
