package com.example.data.di


import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.local.LocalDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
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
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {


    private const val BASE_URL_FAKE_STORE = "https://fakestoreapi.com/"
    private const val BASE_URL_DUMMY_JSON = "https://dummyjson.com/"
    private const val BASE_URL =
        "https://raw.githubusercontent.com/Orlandroid/Resources_Repos/main/fakesResponsesApis/"
    private const val RETROFIT_FAKE_STORE = "FakeStore"
    private const val RETROFIT_DUMMY_JSON = "DummyJson"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun createGenericRetrofit(okHttpClient: OkHttpClient, baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

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
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideDummyJsonApi(@Named(RETROFIT_DUMMY_JSON) retrofit: Retrofit) =
        retrofit.create(DummyJsonApi::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreService(@Named(RETROFIT_FAKE_STORE) retrofit: Retrofit) =
        retrofit.create(FakeStoreService::class.java)


}