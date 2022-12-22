package com.example.citassalon.data.mappers

import com.example.citassalon.data.db.entities.ProductDb
import com.example.citassalon.data.models.remote.Product
import com.example.citassalon.data.models.remote.Rating

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

fun List<ProductDb>.toProductList(): List<Product> {
    val products = arrayListOf<Product>()
    this.forEach {
        products.add(it.toProduct())
    }
    return products
}

fun List<Product>.toProductDbtList(): List<ProductDb> {
    val products = arrayListOf<ProductDb>()
    this.forEach {
        products.add(it.toProductDb())
    }
    return products
}