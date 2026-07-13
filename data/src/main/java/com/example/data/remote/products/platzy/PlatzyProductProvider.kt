package com.example.data.remote.products.platzy

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.data.remote.products.commons.product.ProductProvider
import javax.inject.Inject
import com.example.domain.entities.remote.products.Category

class PlatzyProductProvider @Inject constructor(
    private val api: PlatzyApi
) : ProductProvider, CategoryProvider {
    override suspend fun getProducts() = api.getProducts().map { it.toDomain() }
    override suspend fun getSingleProduct(id: Int) = api.getSingleProduct(id).toDomain()
    override suspend fun getCategories() =
        api.getCategories().map {
            Category(
                id = it.id.toString(),
                name = it.name,
                image = it.image,
                slug = it.slug
            )
        }
}