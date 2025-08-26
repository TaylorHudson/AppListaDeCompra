package com.thudson.lista_de_compras.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.thudson.lista_de_compras.database.entity.ShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Transaction
    @Query("SELECT * FROM shopping_list ORDER BY created_at DESC")
    fun getAll(): Flow<List<ShoppingList>>

    @Query("SELECT * FROM shopping_list WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ShoppingList?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(list: ShoppingList): Long

    @Update
    suspend fun update(list: ShoppingList)

    @Delete
    suspend fun delete(list: ShoppingList)

}