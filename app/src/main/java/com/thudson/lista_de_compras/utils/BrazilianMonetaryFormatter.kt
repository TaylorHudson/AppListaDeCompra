package com.thudson.lista_de_compras.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class BrazilianMoneyFormatter {

    private val formatter: DecimalFormat = (NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat).apply {
        val symbols = decimalFormatSymbols
        symbols.currencySymbol = ""
        decimalFormatSymbols = symbols
    }

    fun format(value: BigDecimal): String = formatter.format(value)

    fun parseDigitsOnly(input: String): BigDecimal {
        val clean = input.filter { it.isDigit() }
        return if (clean.isBlank()) BigDecimal.ZERO else BigDecimal(clean).divide(BigDecimal(100))
    }
}
