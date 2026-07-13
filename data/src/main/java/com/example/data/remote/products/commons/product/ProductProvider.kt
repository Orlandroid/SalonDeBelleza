package com.example.data.remote.products.commons.product

import com.example.domain.entities.remote.products.Product

interface ProductProvider {
    suspend fun getProducts(): List<Product>
    suspend fun getSingleProduct(id: Int): Product
}