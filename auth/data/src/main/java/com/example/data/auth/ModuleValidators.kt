package com.example.data.auth

import com.example.data.AndroidEmailValidator
import com.example.data.MainPasswordValidator
import com.example.domain.EmailValidator
import com.example.domain.PasswordValidator
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