package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class DummyUsersResponseDto(
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("skip")
    val skip: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("users")
    val users: List<UserDto> = emptyList()
)

data class AddressDto(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("coordinates")
    val coordinates: CoordinatesDto = CoordinatesDto(),
    @SerializedName("postalCode")
    val postalCode: String = "",
    @SerializedName("state")
    val state: String = ""
)

data class BankDto(
    @SerializedName("cardExpire")
    val cardExpire: String = "",
    @SerializedName("cardNumber")
    val cardNumber: String = "",
    @SerializedName("cardType")
    val cardType: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("iban")
    val iban: String = ""
)

data class HairDto(
    @SerializedName("color")
    val color: String = "",
    @SerializedName("type")
    val type: String = ""
)

data class UserDto(
    @SerializedName("address")
    val address: AddressDto = AddressDto(),
    @SerializedName("age")
    val age: Int = 0,
    @SerializedName("bank")
    val bank: BankDto = BankDto(),
    @SerializedName("birthDate")
    val birthDate: String = "",
    @SerializedName("bloodGroup")
    val bloodGroup: String = "",
    @SerializedName("company")
    val company: CompanyDto = CompanyDto(),
    @SerializedName("domain")
    val domain: String = "",
    @SerializedName("ein")
    val ein: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("eyeColor")
    val eyeColor: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("hair")
    val hair: HairDto = HairDto(),
    @SerializedName("height")
    val height: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("ip")
    val ip: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("macAddress")
    val macAddress: String = "",
    @SerializedName("maidenName")
    val maidenName: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("ssn")
    val ssn: String = "",
    @SerializedName("university")
    val university: String = "",
    @SerializedName("userAgent")
    val userAgent: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("weight")
    val weight: Double = 0.0
)

data class CoordinatesDto(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0
)

data class CompanyDto(
    @SerializedName("address")
    val address: AddressDto = AddressDto(),
    @SerializedName("department")
    val department: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("title")
    val title: String = ""
)
