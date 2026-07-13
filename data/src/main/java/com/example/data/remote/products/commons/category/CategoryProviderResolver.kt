package com.example.data.remote.products.commons.category

import com.example.data.remote.products.fakestore.FakeStoreCategoryProvider
import com.example.data.remote.products.platzy.PlatzyCategoryProvider
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
