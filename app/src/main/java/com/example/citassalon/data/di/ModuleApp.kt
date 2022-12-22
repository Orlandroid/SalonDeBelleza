package com.example.citassalon.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.citassalon.data.preferences.LoginPeferences
import com.example.citassalon.data.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    private const val SHARE_PREFERENCES = "Skeduly"

    @Singleton
    @Provides
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager {
        return PreferencesManager(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideLoginPreferences(sharedPreferences: SharedPreferences): LoginPeferences {
        return LoginPeferences(sharedPreferences)
    }


}