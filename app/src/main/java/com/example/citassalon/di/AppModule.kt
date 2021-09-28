package com.example.citassalon.di

import android.content.Context
import androidx.room.Room
import com.example.citassalon.data.room.SkedulyDatabase
import com.example.citassalon.data.room.StaffDao
import com.example.citassalon.data.room.StaffRepository
import com.example.citassalon.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {


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
    fun provideStaffDao(db: SkedulyDatabase) = db.staffDao()

    @Singleton
    @Provides
    fun provideStaffRepository(staffDao: StaffDao) = StaffRepository(staffDao)


}