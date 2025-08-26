package com.thudson.lista_de_compras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thudson.lista_de_compras.database.entity.ShoppingList
import com.thudson.lista_de_compras.database.dao.ShoppingListDao

@Database(
    entities = [ShoppingList::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}