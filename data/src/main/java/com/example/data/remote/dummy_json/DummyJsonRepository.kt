package com.example.data.remote.dummy_json

import com.example.domain.entities.remote.categories.Category

interface DummyJsonRepository {
    suspend fun getCategories(): List<Category>
}