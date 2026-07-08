package com.example.data.di.modules


import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.local.RoomLocalDataSource
import com.example.data.remote.RemoteDataSourceImpl
import com.example.data.remote.products.dummyjson.DummyJsonApiV2
import com.example.data.remote.products.fakestore.FakeStoreApi
import com.example.data.remote.products.mydummyapi.MyDummyApi
import com.example.data.remote.products.platzy.PlatzyApi
import com.example.domain.LocalDataSource
import com.example.domain.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(impl: RoomLocalDataSource): LocalDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {


    private const val BASE_URL_FAKE_STORE = "https://fakestoreapi.com/"
    private const val BASE_URL_DUMMY_JSON = "https://dummyjson.com/"
    private const val BASE_URL_PLATZY = "https://api.escuelajs.co/"
    private const val BASE_URL_MY_DUMMY_JSON = "https://api.mydummyapi.com/categories/"
    private const val BASE_URL =
        "https://raw.githubusercontent.com/Orlandroid/Resources_Repos/main/fakesResponsesApis/"
    private const val RETROFIT_FAKE_STORE = "FakeStore"
    private const val RETROFIT_DUMMY_JSON = "DummyJson"
    private const val RETROFIT_PLATZY = "Platzy"
    private const val RETROFIT_MY_DUMMY_JSON = "MyDummyJson"


    //Todo add bulid config for only intercept in debug mode
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).retryOnConnectionFailure(true).build()
    }

    private fun createGenericRetrofit(okHttpClient: OkHttpClient, baseUrl: String) =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        createGenericRetrofit(okHttpClient, BASE_URL)

    @Singleton
    @Provides
    @Named(RETROFIT_FAKE_STORE)
    fun provideRetrofitFakeStore(okHttpClient: OkHttpClient): Retrofit =
        createGenericRetrofit(okHttpClient, BASE_URL_FAKE_STORE)

    @Singleton
    @Provides
    @Named(RETROFIT_DUMMY_JSON)
    fun provideRetroDummyJson(okHttpClient: OkHttpClient): Retrofit =
        createGenericRetrofit(okHttpClient, BASE_URL_DUMMY_JSON)

    @Singleton
    @Provides
    @Named(RETROFIT_PLATZY)
    fun provideRetrofitPlatzy(okHttpClient: OkHttpClient): Retrofit =
        createGenericRetrofit(okHttpClient, BASE_URL_PLATZY)

    @Singleton
    @Provides
    @Named(RETROFIT_MY_DUMMY_JSON)
    fun provideRetroMyDummyJson(okHttpClient: OkHttpClient): Retrofit =
        createGenericRetrofit(okHttpClient, BASE_URL_MY_DUMMY_JSON)

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideDummyJsonApi(@Named(RETROFIT_DUMMY_JSON) retrofit: Retrofit) =
        retrofit.create(DummyJsonApi::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreService(@Named(RETROFIT_FAKE_STORE) retrofit: Retrofit) =
        retrofit.create(FakeStoreService::class.java)

    @Singleton
    @Provides
    fun provideDummyJsonApiV2(@Named(RETROFIT_DUMMY_JSON) retrofit: Retrofit) =
        retrofit.create(DummyJsonApiV2::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreApi(@Named(RETROFIT_FAKE_STORE) retrofit: Retrofit) =
        retrofit.create(FakeStoreApi::class.java)

    @Singleton
    @Provides
    fun provideMyDummyApi(@Named(RETROFIT_MY_DUMMY_JSON) retrofit: Retrofit) =
        retrofit.create(MyDummyApi::class.java)

    @Singleton
    @Provides
    fun providePlatzyApi(@Named(RETROFIT_PLATZY) retrofit: Retrofit) =
        retrofit.create(PlatzyApi::class.java)


}