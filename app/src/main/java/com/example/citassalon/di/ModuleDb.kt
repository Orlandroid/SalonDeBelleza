package com.example.citassalon.di

import android.content.Context
import androidx.room.Room
import com.example.citassalon.data.db.SkedulyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object ModuleDb {

    private const val DATABASE_NAME = "Skeduly"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): SkedulyDatabase {
        return Room.databaseBuilder(
            context,
            SkedulyDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAppointmentDao(db: SkedulyDatabase) = db.appointmentDao()

    @Singleton
    @Provides
    fun provideProductsDao(db: SkedulyDatabase) = db.productDao()
}