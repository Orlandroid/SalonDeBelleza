package com.example.data.remote.products

import com.example.domain.entities.remote.products.Product
import com.example.domain.entities.remote.products.ProductResponse


interface ProductsRepository {

    suspend fun getProducts(): ProductResponse

    suspend fun getSingleProduct(productId:Int): Product

    suspend fun getCategories(): List<String>
}