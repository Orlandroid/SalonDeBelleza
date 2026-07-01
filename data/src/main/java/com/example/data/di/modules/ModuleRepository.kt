package com.example.data.di.modules


import com.example.data.Repository
import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.di.AppointmentsRef
import com.example.data.di.UsersRef
import com.example.data.firebase.FireBaseSource
import com.example.data.remote.appointments.AppointmentsRepository
import com.example.data.remote.appointments.AppointmentsRepositoryImpl
import com.example.data.remote.auth.AuthRepository
import com.example.data.remote.auth.AuthRepositoryImp
import com.example.data.remote.dummy_json.DummyJsonRepository
import com.example.data.remote.dummy_json.DummyJsonRepositoryImp
import com.example.data.remote.fake_store.FakeStoreRepository
import com.example.data.remote.fake_store.FakeStoreRepositoryImp
import com.example.data.remote.user.UserRepository
import com.example.data.remote.user.UserRepositoryImpl
import com.example.domain.LocalDataSource
import com.example.domain.RemoteDataSource
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
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository = Repository(localDataSource, remoteDataSource)


    @Singleton
    @Provides
    fun provideAuthRepository(firebaseSource: FireBaseSource): AuthRepository =
        AuthRepositoryImp(firebaseSource)

    @Singleton
    @Provides
    fun provideFakeStoreRepository(
        api: FakeStoreService,
        localDataSource: LocalDataSource
    ): FakeStoreRepository =
        FakeStoreRepositoryImp(localDataSource = localDataSource, api = api)

    @Singleton
    @Provides
    fun provideDummyJsonRepository(
        api: DummyJsonApi
    ): DummyJsonRepository =
        DummyJsonRepositoryImp(api = api)

    @Singleton
    @Provides
    fun provideAppointmentsRepository(
        @AppointmentsRef databaseReference: DatabaseReference,
        api: WebServices,
    ): AppointmentsRepository =
        AppointmentsRepositoryImpl(databaseReference = databaseReference, webServices = api)

    @Singleton
    @Provides
    fun provideUserRepository(
        @UsersRef databaseReference: DatabaseReference
    ): UserRepository =
        UserRepositoryImpl(databaseReference = databaseReference)


}