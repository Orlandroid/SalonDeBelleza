package com.example.data.di.modules


import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.di.qualifiers.FakeStoreRetrofit
import com.example.data.di.qualifiers.MyDummyJson
import com.example.data.di.qualifiers.MyDummyRetrofit
import com.example.data.di.qualifiers.PlatzyRetrofit
import com.example.data.local.RoomLocalDataSource
import com.example.data.remote.RemoteDataSourceImpl
import com.example.data.remote.products.dummyjson.DummyJsonApiV2
import com.example.data.remote.products.fakestore.FakeStoreApi
import com.example.data.remote.products.mydummyapi.MyDummyApi
import com.example.data.remote.products.platzy.PlatzyApi
import com.example.data.database.local.LocalDataSource
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
    private const val BASE_URL_MY_DUMMY = "https://api.mydummyapi.com/categories/"
    private const val BASE_URL =
        "https://raw.githubusercontent.com/Orlandroid/Resources_Repos/main/fakesResponsesApis/"
    private const val RETROFIT_DUMMY_JSON = "DummyJson"
    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 30L

    private inline fun <reified T> Retrofit.createApi(): T =
        create(T::class.java)


    //Todo add bulid config for only intercept in debug mode
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).retryOnConnectionFailure(true).build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String) =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL)

    @Singleton
    @Provides
    @FakeStoreRetrofit
    fun provideRetrofitFakeStore(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_FAKE_STORE)

    @Singleton
    @Provides
    @Named(RETROFIT_DUMMY_JSON)
    fun provideRetroDummyJson(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_DUMMY_JSON)

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

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideDummyJsonApi(
        @MyDummyJson retrofit: Retrofit
    ) =
        retrofit.createApi<DummyJsonApi>()

    @Singleton
    @Provides
    fun provideDummyJsonApiV2(
        @MyDummyJson retrofit: Retrofit
    ) =
        retrofit.createApi<DummyJsonApiV2>()

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
    fun provideMyDummyApi(@MyDummyRetrofit retrofit: Retrofit) =
        retrofit.createApi<MyDummyApi>()

    @Singleton
    @Provides
    fun providePlatzyApi(@PlatzyRetrofit retrofit: Retrofit) =
        retrofit.create(PlatzyApi::class.java)


}