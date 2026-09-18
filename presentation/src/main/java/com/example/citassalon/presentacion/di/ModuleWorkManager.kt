package com.example.citassalon.presentacion.di

import android.content.Context
import androidx.work.WorkManager
import com.example.citassalon.presentacion.worker.ReminderManagerImpl
import com.example.domain.repository.ReminderManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleWorkManager {

    private const val TASK_SESSION_MANAGER = "taskSessionManager"

    @Provides
    @Singleton
    @Named(TASK_SESSION_MANAGER)
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideReminderManager(@ApplicationContext context: Context): ReminderManager {
        return ReminderManagerImpl(context)
    }
}
