package com.example.citassalon.di

import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.ui.servicio.ServiceRepository
import com.example.citassalon.ui.staff.StaffRepositoryRemote
import com.example.citassalon.ui.sucursal.SucursalRepository
import com.example.citassalon.data.retrofit.WebServices
import com.example.citassalon.data.room.AppointmentDao
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
    fun provideRepository(
        dao: AppointmentDao,
        webServices: WebServices,
        fireBaseSource: FireBaseSource
    ): Repository =
        Repository(dao, webServices, fireBaseSource)

}