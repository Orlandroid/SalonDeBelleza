package com.example.data.remote.dummyjson

import com.example.data.api.DummyJsonApi
import com.example.domain.entities.categories.Category

class DummyJsonRepositoryImp(
    val api: DummyJsonApi,
) : DummyJsonRepository {
    override suspend fun getCategories(): List<Category> = api.getCategories()
}
