package com.example.data.remote.products.commons

import com.example.domain.entities.remote.products.Product

interface ProductProvider {
    suspend fun getProducts(): List<Product>
}