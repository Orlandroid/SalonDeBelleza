package com.example.citassalon.presentacion.di

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.citassalon.presentacion.main.SessionWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ModuleWorkManager {

    private const val TASK_SESSION_MANAGER = "taskSessionManager"
    private const val REQUEST_SESSION_MANAGER = "requestSessionManager"


    @Provides
    @Singleton
    @Named(TASK_SESSION_MANAGER)
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    @Named(REQUEST_SESSION_MANAGER)
    fun provideRequest() = OneTimeWorkRequestBuilder<SessionWorker>().setInitialDelay(1L, TimeUnit.MINUTES).build()


}