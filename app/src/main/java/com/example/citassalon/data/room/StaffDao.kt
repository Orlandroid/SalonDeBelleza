package com.example.citassalon.data.room

import androidx.room.*
import com.example.citassalon.data.models.Staff

@Dao
interface StaffDao {

    @Insert
    suspend fun insertStaff(staff: Staff)

    @Insert
    suspend fun insertManyStaff(vehicle: List<Staff>)

    @Update
    suspend fun updateStaff(staffname: Staff)

    @Delete
    suspend fun deleteStaff(staff: Staff)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): List<Staff>

    @Query("DELETE  FROM Staff")
    suspend fun deleteAll()

    @Query("SELECT * FROM Staff WHERE id =:id")
    suspend fun getStaffById(id: Int): Staff


}