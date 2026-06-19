package com.example.citassalon.presentacion.di

import com.example.citassalon.presentacion.util.AndroidEmailValidator
import com.example.citassalon.presentacion.util.EmailValidator
import com.example.citassalon.presentacion.util.MainPasswordValidator
import com.example.citassalon.presentacion.util.PasswordValidator
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

    @Provides
    @Singleton
    fun passwordValidator(): PasswordValidator {
        return MainPasswordValidator()
    }

}