package com.example.citassalon.ui.staff

import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.room.StaffDao
import javax.inject.Inject

class StaffRepository @Inject constructor(private val db: StaffDao) {

    suspend fun addStaff(staff: Staff) {
        db.insertStaff(staff)
    }

    suspend fun addManyStaff(staff: List<Staff>) {
        db.insertManyStaff(staff)
    }

    suspend fun getAllStaff(): List<Staff> {
        return db.getAllStaff()
    }

    suspend fun updateStaff(staff: Staff) {
        db.updateStaff(staff)
    }

    suspend fun deleteStaff(staff: Staff) {
        db.deleteStaff(staff)
    }

    suspend fun deleteAllStaff() {
        db.deleteAll()
    }

}