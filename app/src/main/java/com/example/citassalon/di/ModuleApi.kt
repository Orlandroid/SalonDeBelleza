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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("retrofit_store")
    fun provideRetrofitFakeStore(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FAKE_STORE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreService( @Named("retrofit_store")retrofit: Retrofit): FakeStoreService =
        retrofit.create(FakeStoreService::class.java)

    @Singleton
    @Provides
    fun provideRepository(
        dao: AppointmentDao,
        webServices: WebServices,
        fakeStoreService: FakeStoreService,
        fireBaseSource: FireBaseSource
    ): Repository =
        Repository(dao, webServices,fakeStoreService, fireBaseSource)

}