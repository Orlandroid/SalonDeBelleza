package com.example.citassalon.di

import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.retrofit.FakeStoreService
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.data.room.AppointmentDao
import com.example.citassalon.util.BASE_URL
import com.example.citassalon.util.BASE_URL_FAKE_STORE
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

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("retrofit_store")
    fun provideRetrofitFakeStore(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FAKE_STORE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreService(@Named("retrofit_store") retrofit: Retrofit): FakeStoreService =
        retrofit.create(FakeStoreService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        dao: AppointmentDao,
        webServices: WebServices,
        fakeStoreService: FakeStoreService,
        fireBaseSource: FireBaseSource
    ): Repository =
        Repository(dao, webServices, fakeStoreService, fireBaseSource)

}