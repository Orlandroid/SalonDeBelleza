package com.example.data.di.modules


import com.example.data.Repository
import com.example.data.api.DummyJsonApi
import com.example.data.api.FakeStoreService
import com.example.data.api.WebServices
import com.example.data.di.qualifiers.AppointmentsRef
import com.example.data.di.qualifiers.UsersRef
import com.example.data.remote.appointments.AppointmentsRepository
import com.example.data.remote.appointments.AppointmentsRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.data.remote.auth.AuthRepositoryImp
import com.example.data.remote.dummy_json.DummyJsonRepository
import com.example.data.remote.dummy_json.DummyJsonRepositoryImp
import com.example.data.remote.fake_store.FakeStoreRepository
import com.example.data.remote.fake_store.FakeStoreRepositoryImp
import com.example.data.remote.products.CategoryRepository
import com.example.data.remote.products.CategoryRepositoryImpl
import com.example.data.remote.products.ProductRepository
import com.example.data.remote.products.ProductRepositoryImpl
import com.example.data.remote.products.commons.category.CategoryProviderResolver
import com.example.data.remote.products.commons.product.ProductProviderResolver
import com.example.domain.repository.UserRepository
import com.example.data.remote.user.UserRepositoryImpl
import com.example.data.database.local.LocalDataSource
import com.example.data.preferences.LoginPreferences
import com.example.domain.RemoteDataSource
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
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository = Repository(localDataSource, remoteDataSource)


    @Singleton
    @Provides
    fun provideAuthRepository(firebaseSource: FirebaseAuth): AuthRepository =
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
        preferences: LoginPreferences
    ): UserRepository =
        UserRepositoryImpl(databaseReference = databaseReference, loginPreferences = preferences)

    @Singleton
    @Provides
    fun provideProductsRepository(
        resolver: ProductProviderResolver
    ): ProductRepository =
        ProductRepositoryImpl(resolver = resolver)


    @Singleton
    @Provides
    fun provideCategoriesRepository(
        categoryResolver: CategoryProviderResolver
    ): CategoryRepository =
        CategoryRepositoryImpl(categoryResolver = categoryResolver)


}