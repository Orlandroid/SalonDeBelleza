package com.example.data.di.modules


import com.example.data.di.AppointmentsRef
import com.example.data.di.ImagesRef
import com.example.data.di.UsersRef
import com.example.data.firebase.FireBaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleFirebase {

    private const val IMAGE_USER = "imageUser"
    private const val APPOINTMENT_PATH = "Appointment"
    private const val USERS_PATH = "users"


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
    @AppointmentsRef
    fun provideFirebaseRealtimeDatabaseReferenceAppointment(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_PATH).child(uuidUser!!)
    }

    @Singleton
    @Provides
    @UsersRef
    fun provideFirebaseRealtimeDatabaseReferenceUsers(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(USERS_PATH).child(uuidUser!!)
    }

    @Singleton
    @Provides
    @ImagesRef
    fun provideFirebaseRealtimeImageReference(
        firebaseDatabase: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(IMAGE_USER).child(uuidUser!!)
    }

}