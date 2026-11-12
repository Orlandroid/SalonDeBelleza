package com.example.data.remote.model

import com.example.domain.entities.remote.migration.BranchesResponse
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Schedule
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.entities.remote.migration.Sucursal

fun BranchesResponseDto.toDomain(): BranchesResponse = BranchesResponse(
    sucursales = sucursales.map { it.toDomain() }
)

fun NegoInfoDto.toDomain(): NegoInfo = NegoInfo(
    sucursal = sucursal.toDomain(),
    staffs = staffs.map { it.toDomain() },
    services = services.map { it.toDomain() }
)

fun SucursalDto.toDomain(): Sucursal = Sucursal(
    id = id,
    lat = lat,
    long = long,
    name = name,
    schedule = schedule.toDomain(),
    isOpen = isOpen
)

fun ScheduleDto.toDomain(): Schedule = Schedule(
    morningOpen = morningOpen,
    morningClose = morningClose,
    afternoonOpen = afternoonOpen,
    afternoonClose = afternoonClose
)

fun ServiceDto.toDomain(): Service = Service(
    id = id,
    name = name,
    precio = precio,
    durationInMinutes = durationInMinutes,
    isSelect = isSelect
)

fun StaffDto.toDomain(): Staff = Staff(
    initials = initials,
    id = id,
    image_url = imageUrl,
    name = name,
    gender = gender,
    rating = rating
)
