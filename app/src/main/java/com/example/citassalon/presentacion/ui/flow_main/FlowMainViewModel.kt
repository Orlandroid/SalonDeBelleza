package com.example.citassalon.presentacion.ui.flow_main

import androidx.lifecycle.ViewModel
import com.example.citassalon.data.models.remote.migration.Service
import com.example.citassalon.data.models.remote.migration.Staff
import com.example.citassalon.data.models.remote.migration.Sucursal

class FlowMainViewModel : ViewModel() {
    var listOfStaffs: List<Staff> = arrayListOf()
    var listOfServices: List<Service> = arrayListOf()
    var sucursal: Sucursal = Sucursal(id = "", lat = "", long = "", name = "")
    var currentStaff = Staff("", "", "", "", 0)
    var dateAppointment = ""
    var hourAppointment = ""
}