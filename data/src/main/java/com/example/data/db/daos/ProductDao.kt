package com.example.data.db.daos

import androidx.room.*
import com.example.domain.entities.db.ProductDb

import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProductDb(productDb: ProductDb):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertManyProductDb(productDb: List<ProductDb>)

    @Update
    suspend fun updateProductDb(productDb: ProductDb)

    @Delete
    suspend fun deleteProductDb(productDb: ProductDb): Int

    @Query("SELECT * FROM ProductDb")
    fun getAllProductDb(): Flow<List<ProductDb>>

    @Query("SELECT * FROM ProductDb")
    fun getAllProductDbCache(): List<ProductDb>

    @Query("SELECT * FROM ProductDb WHERE id =:id")
    fun getProductDbById(id: Int): Flow<ProductDb>

    @Query("DELETE  FROM ProductDb")
    suspend fun deleteAll()

}