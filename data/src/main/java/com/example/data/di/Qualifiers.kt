package com.example.data.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppointmentsRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsersRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImagesRef

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlatzyRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyDummyRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FakeStoreRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyDummyJson