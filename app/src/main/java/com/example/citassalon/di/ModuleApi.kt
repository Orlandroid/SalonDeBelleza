package com.example.citassalon.di

import com.example.citassalon.ui.servicio.ServiceRepository
import com.example.citassalon.ui.staff.StaffRepositoryRemote
import com.example.citassalon.ui.sucursal.SucursalRepository
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideWebService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    fun provideSucursalRepository(webServices: WebServices): SucursalRepository =
        SucursalRepository(webServices)

    @Singleton
    @Provides
    fun provideServicesRepository(webServices: WebServices) = ServiceRepository(webServices)

    @Singleton
    @Provides
    fun provideStaffRepositoryRemote(webServices: WebServices) = StaffRepositoryRemote(webServices)

}