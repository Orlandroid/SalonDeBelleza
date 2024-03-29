package com.example.citassalon.presentacion.features.flow_main

import androidx.lifecycle.ViewModel
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import com.example.domain.entities.remote.migration.Sucursal

class FlowMainViewModel : ViewModel() {
    var listOfStaffs: List<Staff> = arrayListOf()
    var listOfServices: List<Service> = arrayListOf()
    var sucursal: Sucursal = Sucursal(id = "", lat = "", long = "", name = "")
    var currentStaff = Staff("", "", "", "", 0)
    var dateAppointment = ""
    var hourAppointment = ""
}