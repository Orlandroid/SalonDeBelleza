package com.example.citassalon.domain.entities.remote.migration

data class SucursalesResponse(
    val sucursales: List<NegoInfo>
)

data class NegoInfo(
    val sucursal: Sucursal,
    val staffs: List<Staff>,
    val services: List<Service>,
)


data class Sucursal(
    val id: String,
    val lat: String,
    val long: String,
    var name: String
)


data class Service(
    val id: String,
    val name: String,
    val precio: Int,
    var isSelect: Boolean = false
)

data class Staff(
    val id: String,
    val image_url: String,
    val nombre: String,
    val sexo: String,
    val valoracion: Int
)

