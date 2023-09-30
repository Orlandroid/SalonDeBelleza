package com.example.data.di


import com.example.data.Repository
import com.example.data.api.DummyJsonApi
import com.example.data.remote.auth.AuthRepository
import com.example.data.remote.auth.AuthRepositoryImp
import com.example.data.remote.products.ProductsRepository
import com.example.data.remote.products.ProductsRepositoryImp
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
    fun provideProductRepository(api: DummyJsonApi): ProductsRepository = ProductsRepositoryImp(api)

    @Singleton
    @Provides
    fun provideAuthRepository(api: DummyJsonApi): AuthRepository = AuthRepositoryImp(api)


}