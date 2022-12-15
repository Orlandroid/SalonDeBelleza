package com.example.citassalon.data.db

import androidx.room.*
import com.example.citassalon.data.db.entities.ProductDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProductDb(productDb: ProductDb)

    @Insert
    suspend fun insertManyProductDb(productDb: List<ProductDb>)

    @Update
    suspend fun updateProductDb(productDb: ProductDb)

    @Delete
    suspend fun deleteProductDb(productDb: ProductDb): Int

    @Query("SELECT * FROM ProductDb")
    fun getAllProductDb(): Flow<List<ProductDb>>

    @Query("SELECT * FROM ProductDb WHERE id =:id")
    fun getProductDbById(id: Int): Flow<ProductDb>

    @Query("DELETE  FROM ProductDb")
    suspend fun deleteAll()

}