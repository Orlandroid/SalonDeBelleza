package com.example.citassalon.data.models.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.citassalon.data.models.Staff
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class StaffRepository( private val staffDao: StaffDao,
                       private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    {

        fun getStaffs(): List<StaffName>{
            return staffDao.getStaffs()
        }
        suspend fun removeStaff(staff: StaffName){
            coroutineScope {
                launch { staffDao.removeStaff(staff ) }
            }
        }

        suspend fun addStaff(staff: StaffName){
            coroutineScope {
                launch { staffDao.insertStaff(staff) }
            }
        }


        suspend fun populateStaffs(staffs: List<StaffName>)= withContext(ioDispatcher){
            return@withContext staffDao.insertAll(staffs)
        }
    }