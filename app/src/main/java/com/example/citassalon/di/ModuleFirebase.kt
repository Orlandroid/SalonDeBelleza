package com.example.citassalon.di

import com.example.citassalon.data.firebase.FireBaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleFirebase {

    private const val APPOINTMENT_REFERENCE = "Appointment"

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
    fun provideFirebaseRealtimeInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseRealtimeDatabaseReference(firebaseDatabase: FirebaseDatabase,firebaseAuth: FirebaseAuth): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE).child(uuidUser!!)
    }

    @Singleton
    @Provides
    @Named("firebase_url_user")
    fun provideUrlDatabaseUserFromFirebase(firebaseDatabase: FirebaseDatabase,firebaseAuth: FirebaseAuth): String{
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_REFERENCE).child(uuidUser!!).toString()
    }



}