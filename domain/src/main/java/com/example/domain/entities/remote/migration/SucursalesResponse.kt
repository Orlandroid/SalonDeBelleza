package com.example.domain.entities.remote.migration

import com.google.gson.annotations.SerializedName

data class SucursalesResponse(
    val sucursales: List<NegoInfo>
)

data class NegoInfo(
    @SerializedName("sucursal")
    val sucursal: Sucursal,
    val staffs: List<Staff>,
    val services: List<Service>,
) {
    companion object {

        fun mockBusinessList() = listOf(
            mockBusiness(),
            mockBusiness(),
            mockBusiness(),
            mockBusiness(),
        )

        private fun mockBusiness() = NegoInfo(
            sucursal = Sucursal.mockBranch(),
            staffs = Staff.mockStaffList(),
            services = Service.mockListServices()
        )
    }
}


data class Sucursal(
    val id: String,
    val lat: String,
    val long: String,
    var name: String
) {
    companion object {
        fun mockBranch() = Sucursal(id = "", lat = "", long = "", name = "dummyBranch")
    }
}


data class Service(
    val id: String,
    val name: String,
    val precio: Int,
    var isSelect: Boolean = false
) {
    companion object {
        private fun mockService() = Service(id = "0", name = "Corte de pelo", precio = 150)

        fun mockListServices() = listOf(
            mockService(),
            mockService(),
            mockService(),
            mockService(),
            mockService()
        )
    }
}

data class Staff(
    val id: String,
    val image_url: String,
    val nombre: String,
    val sexo: String,
    val valoracion: Int
) {
    companion object {
        fun mockStaff() =
            Staff(id = "", image_url = "", nombre = "Orlando", sexo = "Hombre", valoracion = 4)

        fun mockStaffList() = listOf(
            mockStaff(),
            mockStaff(),
            mockStaff(),
            mockStaff(),
            mockStaff(),
        )
    }
}

