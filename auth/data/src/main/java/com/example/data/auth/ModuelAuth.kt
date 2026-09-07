package com.example.data.auth

import android.content.Context
import com.example.domain.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleRepository {


    @Singleton
    @Provides
    fun provideLoginPreferences(@ApplicationContext context: Context): UserPreferences {
        return LoginPreferences(context = context)
    }
}
