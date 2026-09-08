package com.example.data.products.commons.category

import com.example.data.products.fakestore.FakeStoreCategoryProvider
import com.example.data.products.platzy.PlatzyCategoryProvider
import com.example.domain.CategorySource
import javax.inject.Inject


class CategoryProviderResolver @Inject constructor(
    private val fakeProvider: FakeStoreCategoryProvider,
    private val platziProvider: PlatzyCategoryProvider
) {

    fun resolve(source: CategorySource): CategoryProvider {
        return when (source) {
            CategorySource.FAKE_STORE -> fakeProvider
            CategorySource.PLATZI -> platziProvider
        }
    }
}
