package com.example.domain.mappers

import com.example.domain.entities.db.ProductDb
import com.example.domain.entities.remote.FakeStoreProduct
import com.example.domain.entities.remote.Rating

fun FakeStoreProduct.toProductDb(): ProductDb = ProductDb(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rate = rating.rate,
    count = rating.count,
    userUi = ""
)

fun ProductDb.toProduct() = FakeStoreProduct(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    imageBase64 = image,
    rating = Rating(rate, count)
)
