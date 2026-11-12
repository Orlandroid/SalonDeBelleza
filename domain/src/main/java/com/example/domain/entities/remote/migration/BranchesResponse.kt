package com.example.domain.entities.remote.migration

data class BranchesResponse(
    val sucursales: List<NegoInfo>
)

data class NegoInfo(
    val sucursal: Sucursal,
    val staffs: List<Staff>,
    val services: List<Service>
)

data class Sucursal(
    val id: String,
    val lat: String,
    val long: String,
    val name: String,
    val schedule: Schedule,
    val isOpen: Boolean = false
)

data class Schedule(
    val morningOpen: String,
    val morningClose: String,
    val afternoonOpen: String,
    val afternoonClose: String
)

data class Service(
    val id: String,
    val name: String,
    val precio: Int,
    val durationInMinutes: Int,
    val isSelect: Boolean = false
) {
    companion object {
        private fun mockService() = Service(id = "0", name = "Corte de pelo", precio = 150, durationInMinutes = 30)

        fun mockListServices() =
            listOf(
                mockService(),
                mockService().copy(id = "1"),
                mockService().copy(id = "2"),
                mockService().copy(id = "3"),
                mockService().copy(id = "4")
            )
    }
}

data class Staff(
    val initials: String = "",
    val id: String,
    val image_url: String,
    val name: String?,
    val gender: String,
    val rating: Int
) {
    companion object {
        fun mockStaff() = Staff(id = "", image_url = "", name = "Orlando", gender = "Hombre", rating = 4)

        fun mockStaffList() =
            listOf(
                mockStaff(),
                mockStaff(),
                mockStaff(),
                mockStaff(),
                mockStaff()
            )
    }
}
