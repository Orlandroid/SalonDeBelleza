package com.example.citassalon.presentacion.di

import com.example.domain.validation.AndroidEmailValidator
import com.example.domain.validation.EmailValidator
import com.example.domain.validation.MainPasswordValidator
import com.example.domain.validation.PasswordValidator
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