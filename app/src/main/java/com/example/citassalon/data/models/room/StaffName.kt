package com.example.citassalon.data.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class StaffName constructor(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    @ColumnInfo(name = "staff_name") var staffName: String,
    @ColumnInfo(name="is_working") val isWorking: Boolean
    )

