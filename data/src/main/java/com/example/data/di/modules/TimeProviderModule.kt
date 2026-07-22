package com.example.data.di.modules

import com.example.data.timeprovider.DefaultTimeProvider
import com.example.domain.timeprovider.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(
        implementation: DefaultTimeProvider
    ): TimeProvider
}