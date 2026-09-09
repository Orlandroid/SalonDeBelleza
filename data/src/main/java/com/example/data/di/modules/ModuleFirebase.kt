package com.example.data.di.modules


import com.example.di.qualifiers.AppointmentsRef
import com.example.di.qualifiers.ImagesRef
import com.example.di.qualifiers.LoyaltyRef
import com.example.di.qualifiers.LoyaltyTransactionsRef
import com.example.di.qualifiers.PromotionCodesRef
import com.example.di.qualifiers.RewardsRef
import com.example.di.qualifiers.TransactionReference
import com.example.di.qualifiers.UsersRef
import com.example.di.qualifiers.WalletReference
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
    private const val WALLET = "wallets"
    private const val TRANSACTIONS = "transactions"
    private const val LOYALTY = "loyalty"
    private const val LOYALTY_TRANSACTIONS = "loyaltyTransactions"
    private const val REWARDS = "rewards"
    private const val PROMOTION_CODES = "promotionCodes"


    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseRealtimeInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()


    @Singleton
    @Provides
    @AppointmentsRef
    fun provideFirebaseRealtimeDatabaseReferenceAppointment(
        firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(APPOINTMENT_PATH).child(uuidUser!!)
    }

    @Singleton
    @Provides
    @UsersRef
    fun provideFirebaseRealtimeDatabaseReferenceUsers(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(USERS_PATH)
    }

    @Singleton
    @Provides
    @ImagesRef
    fun provideFirebaseRealtimeImageReference(
        firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth
    ): DatabaseReference {
        val uuidUser = firebaseAuth.uid
        return firebaseDatabase.reference.child(IMAGE_USER).child(uuidUser!!)
    }

    @Singleton
    @Provides
    @WalletReference
    fun provideFirebaseRealtimeWalletReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(WALLET)
    }


    @Singleton
    @Provides
    @TransactionReference
    fun provideFirebaseRealtimeTransactionReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(TRANSACTIONS)
    }

    @Singleton
    @Provides
    @LoyaltyRef
    fun provideFirebaseRealtimeLoyaltyReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(LOYALTY)
    }

    @Singleton
    @Provides
    @LoyaltyTransactionsRef
    fun provideFirebaseRealtimeLoyaltyTransactionsReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(LOYALTY_TRANSACTIONS)
    }

    @Singleton
    @Provides
    @RewardsRef
    fun provideFirebaseRealtimeRewardsReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(REWARDS)
    }

    @Singleton
    @Provides
    @PromotionCodesRef
    fun provideFirebaseRealtimePromotionCodesReference(
        firebaseDatabase: FirebaseDatabase
    ): DatabaseReference {
        return firebaseDatabase.reference.child(PROMOTION_CODES)
    }

}