package com.example.citassalon.presentacion.di

import com.example.citassalon.presentacion.util.AndroidEmailValidator
import com.example.citassalon.presentacion.util.EmailValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleEmailValidator {


    @Provides
    @Singleton
    fun provideAndroidEmailValidator(): EmailValidator {
        return AndroidEmailValidator()
    }

}