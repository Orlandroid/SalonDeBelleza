package com.example.citassalon.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.citassalon.data.db.entities.CategoryDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: CategoryDb): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addManyCategories(category: List<CategoryDb>)

    @Query("SELECT * FROM CategoryDb")
    fun getCategoriesFlow(): Flow<List<CategoryDb>>

    @Query("SELECT * FROM CategoryDb")
    fun getCategories(): List<CategoryDb>
}