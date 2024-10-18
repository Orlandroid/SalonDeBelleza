package com.example.domain.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rate: Double,
    val count: Int,
    @ColumnInfo(defaultValue = "")
    val userUi: String
) {

    companion object {
        private fun mockProductDb() = ProductDb(
            id = 1,
            title = "Producto 1",
            price = 100.00,
            description = "Description",
            category = "Category",
            image = "",
            rate = 4.0,
            count = 4,
            userUi = ""
        )

        fun mockProductDbList() = listOf(
            mockProductDb(),
            mockProductDb(),
            mockProductDb(),
            mockProductDb(),
        )
    }
}