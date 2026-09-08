package com.example.data.di.modules

import com.example.data.api.WebServices
import com.example.data.remote.AuthRepositoryImp
import com.example.data.remote.appointments.AppointmentsRepositoryImpl
import com.example.data.remote.transactions.TransactionRepositoryImp
import com.example.data.remote.user.UserRepositoryImpl
import com.example.di.qualifiers.AppointmentsRef
import com.example.di.qualifiers.TransactionReference
import com.example.di.qualifiers.UsersRef
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.transaction.TransactionRepository
import com.example.domain.use_cases.IsBranchOpenUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleRepository {


    @Singleton
    @Provides
    fun provideAppointmentsRepository(
        @AppointmentsRef databaseReference: DatabaseReference,
        api: WebServices,
        isBranchOpenUseCase: IsBranchOpenUseCase
    ): AppointmentsRepository =
        AppointmentsRepositoryImpl(
            databaseReference = databaseReference,
            webServices = api,
            isBranchOpenUseCase = isBranchOpenUseCase
        )

    @Singleton
    @Provides
    fun provideUserRepository(
        @UsersRef databaseReference: DatabaseReference,
        firebaseAuth: FirebaseAuth
    ): UserRepository =
        UserRepositoryImpl(
            databaseReference = databaseReference,
            firebaseAuth = firebaseAuth
        )

    @Singleton
    @Provides
    fun provideTransactionRepository(
        @TransactionReference databaseReference: DatabaseReference,
        firebaseAuth: FirebaseAuth
    ): TransactionRepository =
        TransactionRepositoryImp(
            databaseReference = databaseReference,
            firebaseAuth = firebaseAuth
        )


    @Singleton
    @Provides
    fun provideAuthRepository(firebaseSource: FirebaseAuth): AuthRepository =
        AuthRepositoryImp(firebaseSource)


}