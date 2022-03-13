package com.example.citassalon.di

import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.retrofit.FakeStoreService
import com.example.citassalon.data.retrofit.RickAndMortyService
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.data.room.AppointmentDao
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

    private const val BASE_URL_RICk_API = " https://rickandmortyapi.com/api/"
    private const val BASE_URL = "https://skeduly.herokuapp.com/api/"
    private const val BASE_URL_FAKE_STORE = "https://fakestoreapi.com/"
    private const val RETROFIT_STORE = "retrofit_store"
    private const val RETROFIT_RICK_AND_MORTY = "retrofit_rick_and_morty"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named(RETROFIT_STORE)
    fun provideRetrofitFakeStore(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FAKE_STORE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named(RETROFIT_RICK_AND_MORTY)
    fun provideRetrofitRickAndMorty(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_RICk_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideFakeStoreService( @Named(RETROFIT_STORE)retrofit: Retrofit): FakeStoreService =
        retrofit.create(FakeStoreService::class.java)

    @Singleton
    @Provides
    fun provideRickAndMortyService(@Named(RETROFIT_RICK_AND_MORTY)retrofit: Retrofit): RickAndMortyService =
        retrofit.create(RickAndMortyService::class.java)


    @Singleton
    @Provides
    fun provideRepository(
        dao: AppointmentDao,
        webServices: WebServices,
        fakeStoreService: FakeStoreService,
        fireBaseSource: FireBaseSource,
        rickAndMortyService: RickAndMortyService
    ): Repository =
        Repository(dao, webServices,fakeStoreService, fireBaseSource,rickAndMortyService)

}