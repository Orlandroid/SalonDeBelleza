package com.example.citassalon.domain.mappers

import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.domain.entities.remote.Product
import com.example.citassalon.domain.entities.remote.Rating

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