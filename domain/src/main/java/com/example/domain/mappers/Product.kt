package com.example.domain.mappers

import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.products.Product

fun Product.toProductDb(): ProductDb = ProductDb(
    id = id,
    title = title,
    price = price,
    description = description,
    image = image.orEmpty(),
    rate = rating ?: 0.0,
    userUi = ""
)

fun ProductDb.toProduct() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    rating = rate,
    image = image
)
