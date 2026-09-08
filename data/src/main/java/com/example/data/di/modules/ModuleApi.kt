package com.example.data.di.modules

import com.example.data.api.WebServices
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
object ModuleApi {


    private const val BASE_URL_DUMMY_JSON = "https://dummyjson.com/"
    private const val BASE_URL =
        "https://raw.githubusercontent.com/Orlandroid/Resources_Repos/main/fakesResponsesApis/"
    private const val RETROFIT_DUMMY_JSON = "DummyJson"
    private const val CONNECT_TIMEOUT = 60L
    private const val READ_TIMEOUT = 60L
    private const val WRITE_TIMEOUT = 30L


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
    @Named(RETROFIT_DUMMY_JSON)
    fun provideRetroDummyJson(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BASE_URL_DUMMY_JSON)


    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebServices::class.java)


}