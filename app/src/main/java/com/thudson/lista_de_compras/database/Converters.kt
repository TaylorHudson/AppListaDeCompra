package com.thudson.lista_de_compras.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import java.math.BigDecimal

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromItemsList(value: List<ShoppingItem>): String = gson.toJson(value)

    @TypeConverter
    fun toItemsList(value: String): List<ShoppingItem> {
        val type = object : TypeToken<List<ShoppingItem>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String = value.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String): BigDecimal = BigDecimal(value)
}