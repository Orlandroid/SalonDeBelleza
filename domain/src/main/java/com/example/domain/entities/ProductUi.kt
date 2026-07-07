package com.example.domain.entities

import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.products.Product

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
//
//fun ProductUi.toProduct() = Product(
//    id = id,
//    title = title,
//    price = price,
//    description = description,
//    category = category,
//    image = imageBase64 ?: "",
//    rating = com.example.domain.entities.remote.Rating(rate = rating, count = 0)
//)
//
fun ProductDb.toProduct() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    rating = rate,
    image = image
)

//fun List<ProductDb>.toProductUiList(): List<ProductUi> = map { it.toProductUi() }
