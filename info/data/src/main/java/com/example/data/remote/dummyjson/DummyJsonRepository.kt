package com.example.data.remote.dummyjson

import com.example.domain.entities.categories.Category

interface DummyJsonRepository {
    suspend fun getCategories(): List<Category>
}
