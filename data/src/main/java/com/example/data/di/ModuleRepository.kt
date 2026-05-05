package com.example.data.di


import com.example.data.Repository
import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.firebase.FireBaseSource
import com.example.data.remote.auth.AuthRepository
import com.example.data.remote.auth.AuthRepositoryImp
import com.example.data.remote.dummy_json.DummyJsonRepository
import com.example.data.remote.dummy_json.DummyJsonRepositoryImp
import com.example.data.remote.fake_store.FakeStoreRepository
import com.example.data.remote.fake_store.FakeStoreRepositoryImp
import com.example.domain.LocalDataSource
import com.example.domain.RemoteDataSource
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


}