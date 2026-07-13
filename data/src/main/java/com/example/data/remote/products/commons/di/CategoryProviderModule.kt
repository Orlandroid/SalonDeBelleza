package com.example.data.remote.products.commons.di

import com.example.data.remote.products.commons.category.CategoryProvider
import com.example.data.remote.products.commons.category.CategoryProviderResolver
import com.example.data.remote.products.commons.category.CategorySource
import com.example.data.remote.products.fakestore.FakeStoreApi
import com.example.data.remote.products.fakestore.FakeStoreCategoryProvider
import com.example.data.remote.products.platzy.PlatzyApi
import com.example.data.remote.products.platzy.PlatzyCategoryProvider
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class CategorySourceKey(
    val value: CategorySource
)


@Module
@InstallIn(SingletonComponent::class)
object CategoryProviderModule {

    @Provides
    @IntoMap
    @CategorySourceKey(CategorySource.FAKE_STORE)
    fun provideDummyJsonProvider(
        api: FakeStoreApi
    ): CategoryProvider = FakeStoreCategoryProvider(api = api)

    @Provides
    @IntoMap
    @CategorySourceKey(CategorySource.PLATZI)
    fun providePlatzyProvider(
        api: PlatzyApi
    ): CategoryProvider = PlatzyCategoryProvider(api = api)


    @Provides
    fun provideCategoryProviderResolver(
        fakeProvider: FakeStoreCategoryProvider,
        platziProvider: PlatzyCategoryProvider
    ): CategoryProviderResolver = CategoryProviderResolver(
        fakeProvider,
        platziProvider
    )


}