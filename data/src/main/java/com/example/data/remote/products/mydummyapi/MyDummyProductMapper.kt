package com.example.data.remote.products.mydummyapi

import com.example.domain.entities.remote.products.Product


fun ProductMyDummyApi.toDomain(): Product {
    return Product(
        id = id,
        title = name,
        description = description,
        price = price.toDouble(),
        image = image
    )
}