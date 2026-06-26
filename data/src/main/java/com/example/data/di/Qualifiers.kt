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