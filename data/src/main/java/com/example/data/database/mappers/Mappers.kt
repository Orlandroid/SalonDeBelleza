package com.example.data.database.mappers

import com.example.data.database.entities.CategoryEntity
import com.example.data.database.entities.ProductEntity
import com.example.domain.entities.remote.products.Product

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

fun CategoryEntity.toStringCategory(): String {
    return category
}

fun List<CategoryEntity>.toStringList(): List<String> {
    val listOfCategoryLikeString = arrayListOf<String>()
    forEach {
        listOfCategoryLikeString.add(it.toStringCategory())
    }
    return listOfCategoryLikeString
}

fun String.toCategory(): CategoryEntity {
    return CategoryEntity(0, this)
}

fun List<String>.toListCategoriesString(): List<CategoryEntity> {
    val categories = arrayListOf<CategoryEntity>()
    forEach {
        categories.add(it.toCategory())
    }
    return categories
}
