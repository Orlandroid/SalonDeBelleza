package com.example.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.data.preferences.LoginPreferences
import com.example.data.preferences.PreferencesManager
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
    fun providePreferencesManager(
        @ApplicationContext context: Context,
    ): PreferencesManager {
        return PreferencesManager(context = context)
    }

    @Singleton
    @Provides
    fun provideLoginPreferences(@ApplicationContext context: Context): LoginPreferences {
        return LoginPreferences(context = context)
    }


}