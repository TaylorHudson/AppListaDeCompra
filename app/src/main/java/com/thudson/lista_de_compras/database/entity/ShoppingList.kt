package com.thudson.lista_de_compras.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("name") var name: String,
    @ColumnInfo("created_at") val createdAt: Long = System.currentTimeMillis(),
    var items: List<ShoppingItem> = emptyList(),
) : Parcelable {
    fun duplicate(): ShoppingList {
        return copy(
            id = UUID.randomUUID().toString(),
            name = "$name - CÓPIA",
            createdAt = System.currentTimeMillis(),
        )
    }
}