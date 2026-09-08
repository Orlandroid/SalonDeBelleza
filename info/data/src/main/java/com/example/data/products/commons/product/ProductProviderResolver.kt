package com.example.data.products.commons.product

import com.example.data.products.dummyjson.DummyJsonProductProvider
import com.example.data.products.fakestore.FakeStoreProductProvider
import com.example.data.products.mydummyapi.MyDummyProductProvider
import com.example.data.products.platzy.PlatzyProductProvider
import com.example.domain.ProductSource
import javax.inject.Inject


class ProductProviderResolver @Inject constructor(
    private val dummyProvider: DummyJsonProductProvider,
    private val fakeProvider: FakeStoreProductProvider,
    private val platziProvider: PlatzyProductProvider,
    private val productsApiProvider: MyDummyProductProvider
) {

    fun resolve(source: ProductSource): ProductProvider {
        return when (source) {
            ProductSource.DUMMY_JSON -> dummyProvider
            ProductSource.FAKE_STORE -> fakeProvider
            ProductSource.PLATZI -> platziProvider
            ProductSource.MY_DUMMY_API -> productsApiProvider
        }
    }
}