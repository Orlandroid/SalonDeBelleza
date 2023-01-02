package com.example.citassalon.data.mappers

import com.example.citassalon.data.db.entities.CategoryDb


fun CategoryDb.toStringCategory(): String {
    return category
}

fun List<CategoryDb>.toStringList(): List<String> {
    val listOfCategoryLikeString = arrayListOf<String>()
    forEach {
        listOfCategoryLikeString.add(it.toStringCategory())
    }
    return listOfCategoryLikeString
}

fun String.toCategory(): CategoryDb {
    return CategoryDb(0, this)
}

fun List<String>.toListCategoriesString(): List<CategoryDb> {
    val categories = arrayListOf<CategoryDb>()
    forEach {
        categories.add(it.toCategory())
    }
    return categories
}

