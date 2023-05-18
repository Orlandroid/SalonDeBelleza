package com.example.domain.mappers

import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.Product
import com.example.domain.entities.remote.Rating

fun Product.toProductDb(): ProductDb = ProductDb(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = imageBase64,
    rate = rating.rate,
    count = rating.count,
    userUi = ""
 )

fun ProductDb.toProduct(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    imageBase64 = image,
    rating = Rating(rate, count)
)
