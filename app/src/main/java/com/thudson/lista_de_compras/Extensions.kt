package com.thudson.lista_de_compras

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun String.capitalize() = lowercase().trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

fun BigDecimal.toCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}