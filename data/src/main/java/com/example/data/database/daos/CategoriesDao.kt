package com.example.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addManyCategories(category: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    fun getCategoriesFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity")
    fun getCategories(): List<CategoryEntity>
}