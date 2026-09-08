package com.example.data.products.commons.product

import com.example.domain.Product

interface ProductProvider {
    suspend fun getProducts(): List<Product>
    suspend fun getSingleProduct(id: Int): Product
}