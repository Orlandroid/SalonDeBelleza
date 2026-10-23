package com.example.data.database.mappers

import com.example.data.database.entities.CategoryEntity
import com.example.data.database.entities.ProductEntity
import com.example.domain.Product

fun ProductEntity.toProduct() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    rating = rate,
    image = image
)

fun Product.toProductEntity() = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    image = image ?: "",
    rate = rating ?: 0.0
)

