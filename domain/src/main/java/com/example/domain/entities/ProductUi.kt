package com.example.domain.entities

import com.example.domain.entities.db.ProductDb

data class ProductUi(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Double,
    val imageBase64: String? = ""
)

fun ProductDb.toProductUi() = ProductUi(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rate
)

fun List<ProductDb>.toProductUiList(): List<ProductUi> = map { it.toProductUi() }
