package com.example.data.di.modules

import com.example.data.api.WebServices
import com.example.data.remote.AuthRepositoryImp
import com.example.data.remote.appointments.AppointmentsRepositoryImpl
import com.example.data.remote.loyalty.LoyaltyRepositoryImpl
import com.example.data.remote.user.UserRepositoryImpl
import com.example.di.qualifiers.AppointmentsRef
import com.example.di.qualifiers.LoyaltyRef
import com.example.di.qualifiers.LoyaltyTransactionsRef
import com.example.di.qualifiers.PromotionCodesRef
import com.example.di.qualifiers.RewardsRef
import com.example.di.qualifiers.UsersRef
import com.example.domain.repository.AppointmentsRepository
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.LoyaltyRepository
import com.example.domain.repository.UserRepository
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
    fun provideAuthRepository(firebaseSource: FirebaseAuth): AuthRepository =
        AuthRepositoryImp(firebaseSource)

    @Singleton
    @Provides
    fun provideLoyaltyRepository(
        @LoyaltyRef loyaltyRef: DatabaseReference,
        @LoyaltyTransactionsRef loyaltyTransactionsRef: DatabaseReference,
        @RewardsRef rewardsRef: DatabaseReference,
        @PromotionCodesRef promotionCodesRef: DatabaseReference
    ): LoyaltyRepository =
        LoyaltyRepositoryImpl(
            loyaltyRef = loyaltyRef,
            loyaltyTransactionsRef = loyaltyTransactionsRef,
            rewardsRef = rewardsRef,
            promotionCodesRef = promotionCodesRef
        )


}
