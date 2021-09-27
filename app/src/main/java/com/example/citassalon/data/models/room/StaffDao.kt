package com.example.citassalon.data.models.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.citassalon.data.models.Staff
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDao {

    @Insert
    suspend fun insertStaff(staff: StaffName)

    @Insert
    suspend fun insertAll(vehicle: List<StaffName>)

    @Update
    suspend fun updateStaff(staffname: StaffName)

    @Delete
    suspend fun removeStaff(staff: StaffName)

    @Query("DELETE * FROM StaffName WHERE id =: id")
    fun removestaffById(id:Int)

    @Delete
    suspend fun removeStaffs(vararg staffs: StaffName)

    @Query("SELECT * FROM StaffName")
    fun getStaffs(): List<StaffName>

    @Query("SELECT FROM StaffName WHERE id =: id")
    suspend fun getStaffById(id: Int):StaffName


/*



      @Query("SELECT * FROM StaffName WHERE staff_name =: staff")
    suspend fun getItem(staffname: String): Staff


    @Query("SELECT * from StaffName ORDER BY staffId ASC")
    fun getItems(): Flow<List<Staff>>
*/
/*
    @Query("SELECT * FROM StaffName WHERE id = :id")
    fun getStaffById(id: Int): Staff

    @Query("SELECT * FROM StaffName WHERE staff_name =: Staff")
    fun getLoginDetails(username: String?) : LiveData<StaffName>*/
}