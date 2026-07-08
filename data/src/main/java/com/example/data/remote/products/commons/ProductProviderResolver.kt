package com.example.data.remote.products.commons

import com.example.data.remote.products.dummyjson.DummyJsonProductProvider
import com.example.data.remote.products.fakestore.FakeStoreProductProvider
import com.example.data.remote.products.mydummyapi.MyDummyProductProvider
import com.example.data.remote.products.platzy.PlatzyProductProvider
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