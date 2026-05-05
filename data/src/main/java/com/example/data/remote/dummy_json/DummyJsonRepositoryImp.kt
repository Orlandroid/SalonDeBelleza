package com.example.data.remote.dummy_json

import com.example.data.api.DummyJsonApi
import com.example.domain.entities.remote.categories.Category

class DummyJsonRepositoryImp(val api: DummyJsonApi) : DummyJsonRepository {

    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }
}