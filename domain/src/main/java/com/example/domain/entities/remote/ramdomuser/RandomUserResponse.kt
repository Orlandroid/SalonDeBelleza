package com.example.domain.entities.remote.ramdomuser

data class RandomUserResponse(
    val info: Info,
    val results: List<Result>
)

data class Registered(
    val age: Int,
    val date: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Id(
    val name: String,
    val value: String
)


data class Dob(
    val age: Int,
    val date: String
)

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: String,
    val state: String,
    val street: Street,
    val timezone: Timezone
)


data class Login(
    val md5: String,
    val password: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String,
    val uuid: String
)

data class Name(
    val first: String,
    val last: String,
    val title: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class Result(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)

data class Street(
    val name: String,
    val number: Int
)

data class Timezone(
    val description: String,
    val offset: String
)