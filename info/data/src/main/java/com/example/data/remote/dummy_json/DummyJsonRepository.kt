package com.example.data.remote.dummy_json

import com.example.domain.entities.categories.Category

interface DummyJsonRepository {
    suspend fun getCategories(): List<Category>
}