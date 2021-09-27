package com.example.citassalon.data.models.room

import android.app.Application
import androidx.room.Room

class StaffApp: Application() {


    val staffRepository: StaffRepository
    get() = StaffRepository(
        StaffDatabase.getInstance(this)!!.staffDao()
    )

  /*  val room = Room
        .databaseBuilder(this, StaffDatabase::class.java, "staff")
        .build()
*/
        }