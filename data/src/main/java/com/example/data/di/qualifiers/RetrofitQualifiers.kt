package com.example.data.di.qualifiers

import javax.inject.Qualifier

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