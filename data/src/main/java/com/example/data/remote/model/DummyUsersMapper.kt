package com.example.data.remote.model

import com.example.domain.entities.remote.dummyUsers.Address
import com.example.domain.entities.remote.dummyUsers.Bank
import com.example.domain.entities.remote.dummyUsers.Company
import com.example.domain.entities.remote.dummyUsers.Coordinates
import com.example.domain.entities.remote.dummyUsers.DummyUsersResponse
import com.example.domain.entities.remote.dummyUsers.Hair
import com.example.domain.entities.remote.dummyUsers.User

fun DummyUsersResponseDto.toDomain(): DummyUsersResponse = DummyUsersResponse(
    limit = limit,
    skip = skip,
    total = total,
    users = users.map { it.toDomain() }
)

fun UserDto.toDomain(): User = User(
    address = address.toDomain(),
    age = age,
    bank = bank.toDomain(),
    birthDate = birthDate,
    bloodGroup = bloodGroup,
    company = company.toDomain(),
    domain = domain,
    ein = ein,
    email = email,
    eyeColor = eyeColor,
    firstName = firstName,
    gender = gender,
    hair = hair.toDomain(),
    height = height,
    id = id,
    image = image,
    ip = ip,
    lastName = lastName,
    macAddress = macAddress,
    maidenName = maidenName,
    password = password,
    phone = phone,
    ssn = ssn,
    university = university,
    userAgent = userAgent,
    username = username,
    weight = weight
)

fun AddressDto.toDomain(): Address = Address(
    address = address,
    city = city,
    coordinates = coordinates.toDomain(),
    postalCode = postalCode,
    state = state
)

fun BankDto.toDomain(): Bank = Bank(
    cardExpire = cardExpire,
    cardNumber = cardNumber,
    cardType = cardType,
    currency = currency,
    iban = iban
)

fun HairDto.toDomain(): Hair = Hair(
    color = color,
    type = type
)

fun CoordinatesDto.toDomain(): Coordinates = Coordinates(
    lat = lat,
    lng = lng
)

fun CompanyDto.toDomain(): Company = Company(
    address = address.toDomain(),
    department = department,
    name = name,
    title = title
)
