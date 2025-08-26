package com.thudson.lista_de_compras.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal

class MoneyTextWatcher(
    private val editText: EditText,
    private val formatter: BrazilianMoneyFormatter
) : TextWatcher {

    private var current = formatter.format(BigDecimal.ZERO)

    init {
        editText.setText(current)
        editText.setSelection(current.length)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val newText = s.toString()
        if (newText != current) {
            editText.removeTextChangedListener(this)

            val value = formatter.parseDigitsOnly(newText)
            val formatted = formatter.format(value)
            current = formatted

            editText.setText(formatted)
            editText.setSelection(formatted.length)

            editText.addTextChangedListener(this)
        }
    }
}
