package com.example.data.database.daos

import androidx.room.*
import com.example.data.database.entities.ProductEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductDb(productDb: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManyProductDb(productDb: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    fun getAllProductDb(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id =:id")
    fun getProductDbById(id: Int): Flow<ProductEntity>

    @Query("DELETE  FROM ProductEntity")
    suspend fun deleteAll(): Int

}