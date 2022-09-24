package com.example.citassalon.di

import android.content.Context
import androidx.room.Room
import com.example.citassalon.data.room.SkedulyDatabase
import com.example.citassalon.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object ModuleDb {

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


}