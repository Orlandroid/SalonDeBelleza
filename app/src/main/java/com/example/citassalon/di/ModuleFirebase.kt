package com.example.citassalon.di

import com.example.citassalon.data.firebase.FireBaseSource
import com.example.citassalon.data.firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleFirebase {

    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseSource(firebaseAuth: FirebaseAuth): FireBaseSource =
        FireBaseSource(firebaseAuth)

    @Singleton
    @Provides
    fun provideFirebaseRepository(fireBaseSource: FireBaseSource): FirebaseRepository =
        FirebaseRepository(fireBaseSource)

    @Singleton
    @Provides
    fun provideFirebaseRealtimeInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()

}