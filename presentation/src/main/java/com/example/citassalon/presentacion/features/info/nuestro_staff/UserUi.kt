package com.example.citassalon.presentacion.features.info.nuestro_staff

import com.example.domain.entities.remote.dummyUsers.User

data class UserUi(
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String,
    val image: String
)

fun User.toUserUi() = UserUi(
    firstName = firstName,
    lastName = lastName,
    email = email,
    address = address.address,
    image = image
)

fun List<User>.toUserUiList() = map { it.toUserUi() }