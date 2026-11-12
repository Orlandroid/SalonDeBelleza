package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class BranchesResponseDto(
    @SerializedName("sucursales")
    val sucursales: List<NegoInfoDto> = emptyList()
)

data class NegoInfoDto(
    @SerializedName("sucursal")
    val sucursal: SucursalDto,
    @SerializedName("staffs")
    val staffs: List<StaffDto> = emptyList(),
    @SerializedName("services")
    val services: List<ServiceDto> = emptyList()
)

data class SucursalDto(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("lat")
    val lat: String = "",
    @SerializedName("long")
    val long: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("schedule")
    val schedule: ScheduleDto,
    @SerializedName("isOpen")
    val isOpen: Boolean = false
)

data class ScheduleDto(
    @SerializedName("morning_open")
    val morningOpen: String = "",
    @SerializedName("morning_close")
    val morningClose: String = "",
    @SerializedName("afternoon_open")
    val afternoonOpen: String = "",
    @SerializedName("afternoon_close")
    val afternoonClose: String = ""
)

data class ServiceDto(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("precio")
    val precio: Int = 0,
    @SerializedName("durationInMinutes")
    val durationInMinutes: Int = 0,
    @SerializedName("isSelect")
    val isSelect: Boolean = false
)

data class StaffDto(
    @SerializedName("initials")
    val initials: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("nombre")
    val name: String? = null,
    @SerializedName("sexo")
    val gender: String = "",
    @SerializedName("valoracion")
    val rating: Int = 0
)
