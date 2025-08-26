package com.thudson.lista_de_compras.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RequiredTextWatcher(
    private val editText: TextInputEditText,
    private val layout: TextInputLayout,
    private val errorMessage: String
) : TextWatcher {

    private var userHasTyped = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (count > 0) {
            userHasTyped = true
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (userHasTyped) {
            layout.error = if (s.isNullOrEmpty()) errorMessage else null
        }
    }
}
