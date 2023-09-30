package com.example.domain.entities.remote.products

data class ProductResponse(
    val total: Int,
    val skip: Int,
    val limit: Int,
    val products: List<Product>
)

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)