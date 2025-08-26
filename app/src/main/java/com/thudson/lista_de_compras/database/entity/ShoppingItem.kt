package com.thudson.lista_de_compras.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.UUID

@Parcelize
data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo("name") val name: String,
    @ColumnInfo("quantity") val quantity: Int = 1,
    @ColumnInfo("price") val price: BigDecimal = BigDecimal.ZERO,
    @ColumnInfo("is_checked") val isChecked: Boolean = false,
    @ColumnInfo("created_at") val createdAt: Long = System.currentTimeMillis(),
) : Parcelable
