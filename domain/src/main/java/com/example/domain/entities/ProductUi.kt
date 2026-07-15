package com.example.domain.entities

import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.products.Product

fun ProductDb.toProduct() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    rating = rate,
    image = image
)
