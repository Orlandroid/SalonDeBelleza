package com.example.data.remote.products.commons.di


import com.example.data.remote.products.commons.ProductProvider
import com.example.data.remote.products.commons.ProductProviderResolver
import com.example.data.remote.products.commons.ProductSource
import com.example.data.remote.products.dummyjson.DummyJsonApiV2
import com.example.data.remote.products.dummyjson.DummyJsonProductProvider
import com.example.data.remote.products.fakestore.FakeStoreApi
import com.example.data.remote.products.fakestore.FakeStoreProductProvider
import com.example.data.remote.products.mydummyapi.MyDummyApi
import com.example.data.remote.products.mydummyapi.MyDummyProductProvider
import com.example.data.remote.products.platzy.PlatzyApi
import com.example.data.remote.products.platzy.PlatzyProductProvider
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductSourceKey(
    val value: ProductSource
)

@Module
@InstallIn(SingletonComponent::class)
object ProductProviderModule {

    @Provides
    @IntoMap
    @ProductSourceKey(ProductSource.DUMMY_JSON)
    fun provideDummyJsonProvider(
        api: DummyJsonApiV2
    ): ProductProvider =
        DummyJsonProductProvider(api = api)

    @Provides
    @IntoMap
    @ProductSourceKey(ProductSource.FAKE_STORE)
    fun provideFakeStoreProvider(
        api: FakeStoreApi
    ): ProductProvider =
        FakeStoreProductProvider(api)

    @Provides
    @IntoMap
    @ProductSourceKey(ProductSource.PLATZI)
    fun providePlatziProvider(
        api: PlatzyApi
    ): ProductProvider =
        PlatzyProductProvider(api)

    @Provides
    @IntoMap
    @ProductSourceKey(ProductSource.PRODUCTS_API)
    fun provideProductsApiProvider(
        api: MyDummyApi
    ): ProductProvider =
        MyDummyProductProvider(api)

    @Provides
    fun provideProductProviderResolver(
        dummy: DummyJsonProductProvider,
        fake: FakeStoreProductProvider,
        platzi: PlatzyProductProvider,
        productsApi: MyDummyProductProvider
    ): ProductProviderResolver {
        return ProductProviderResolver(
            dummy,
            fake,
            platzi,
            productsApi
        )
    }
}