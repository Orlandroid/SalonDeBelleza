package com.example.citassalon.ui.staff.stafflist

import com.example.citassalon.data.models.room.StaffName

interface ItemListener {
    fun onEdit(staff: StaffName)

    fun onDelete(staff: StaffName)

}