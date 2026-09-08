package com.example.data.di

import com.example.data.DefaultBusinessRepository
import com.example.data.FakeStoreRetrofit
import com.example.data.MyDummyJson
import com.example.data.MyDummyRetrofit
import com.example.data.PlatzyRetrofit
import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.database.daos.ProductDao
import com.example.data.products.CategoryRepositoryImpl
import com.example.data.products.ProductRepositoryImpl
import com.example.data.products.commons.category.CategoryProviderResolver
import com.example.data.products.commons.product.ProductProviderResolver
import com.example.data.products.dummyjson.DummyJsonApiV2
import com.example.data.products.fakestore.FakeStoreApi
import com.example.data.products.mydummyapi.MyDummyApi
import com.example.data.products.platzy.PlatzyApi
import com.example.data.remote.dummy_json.DummyJsonRepository
import com.example.data.remote.dummy_json.DummyJsonRepositoryImp
import com.example.domain.repository.BusinessRepository
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleRepository {

    private const val BASE_URL_FAKE_STORE = "https://fakestoreapi.com/"
    private const val BASE_URL_DUMMY_JSON = "https://dummyjson.com/"
    private const val BASE_URL_PLATZY = "https://api.escuelajs.co/"
    private const val BASE_URL_MY_DUMMY = "https://api.mydummyapi.com/categories/"

    private inline fun <reified T> Retrofit.createApi(): T =
        create(T::class.java)



    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String) =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()


    @Singleton
    @Provides
    fun provideDummyJsonRepository(
        api: DummyJsonApi
    ): DummyJsonRepository =
        DummyJsonRepositoryImp(api = api)

    @Singleton
    @Provides
    fun provideProductsRepository(
        resolver: ProductProviderResolver
    ): ProductRepository =
        ProductRepositoryImpl(resolver = resolver)


    @Singleton
    @Provides
    fun provideCategoriesRepository(
        categoryResolver: CategoryProviderResolver
    ): CategoryRepository =
        CategoryRepositoryImpl(categoryResolver = categoryResolver)

    @Singleton
    @Provides
    fun provideDummyJsonApi(
        @MyDummyJson retrofit: Retrofit
    ) =
        retrofit.createApi<DummyJsonApi>()

    @Singleton
    @Provides
    fun provideFakeStoreService(
        @FakeStoreRetrofit retrofit: Retrofit
    ) =
        retrofit.createApi<FakeStoreService>()


    @Singleton
    @Provides
    fun provideFakeStoreApi(@FakeStoreRetrofit retrofit: Retrofit) =
        retrofit.createApi<FakeStoreApi>()

    @Singleton
    @Provides
    fun provideDummyJsonApiV2(
        @MyDummyJson retrofit: Retrofit
    ) =
        retrofit.createApi<DummyJsonApiV2>()

    @Singleton
    @Provides
    fun provideMyDummyApi(@MyDummyRetrofit retrofit: Retrofit) =
        retrofit.createApi<MyDummyApi>()

    @Singleton
    @Provides
    fun providePlatzyApi(@PlatzyRetrofit retrofit: Retrofit) =
        retrofit.create(PlatzyApi::class.java)

    @Singleton
    @Provides
    fun provideBusinessRepository(
        productDao: ProductDao,
        webServices: WebServices
    ): BusinessRepository =
        DefaultBusinessRepository(productDao = productDao, webServices = webServices)

    @Singleton
    @Provides
    @FakeStoreRetrofit
    fun provideRetrofitFakeStore(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_FAKE_STORE)

    @Singleton
    @Provides
    @PlatzyRetrofit
    fun provideRetrofitPlatzy(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_PLATZY)

    @Singleton
    @Provides
    @MyDummyRetrofit
    fun provideRetroMyDummy(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_MY_DUMMY)

    @Singleton
    @Provides
    @MyDummyJson
    fun provideRetroMyDummyJson(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_DUMMY_JSON)

}