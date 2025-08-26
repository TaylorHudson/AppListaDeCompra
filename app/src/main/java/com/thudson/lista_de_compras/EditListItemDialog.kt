package com.thudson.lista_de_compras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import com.thudson.lista_de_compras.databinding.DialogEditListBinding
import com.thudson.lista_de_compras.database.entity.ShoppingList
import com.thudson.lista_de_compras.databinding.DialogEditListItemBinding
import com.thudson.lista_de_compras.utils.BrazilianMoneyFormatter
import com.thudson.lista_de_compras.utils.MoneyTextWatcher
import com.thudson.lista_de_compras.utils.RequiredTextWatcher
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class EditListItemDialog(
    private val shoppingList: ShoppingList?,
    private val onSave: (ShoppingItem, ShoppingList?) -> Unit
) : DialogFragment() {

    private var listId: String? = null
    private var _binding: DialogEditListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listId = arguments?.getString("list_id")
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditListItemBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext()).setView(binding.root)
        val dialog = builder.create()

        shoppingList?.let { binding.editTextName.setText(it.name) }
        binding.buttonCancel.setOnClickListener { dialog.dismiss() }
        binding.editTextName.addTextChangedListener(
            RequiredTextWatcher(binding.editTextName, binding.editTextNameLayout, "Informe o nome do item")
        )
        binding.editTextQuantity.setText("1")
        binding.decreaseQuantity.setOnClickListener { onDecreaseQuantity() }
        binding.increaseQuantity.setOnClickListener { onIncreaseQuantity() }

        binding.editTextPrice.addTextChangedListener(
            MoneyTextWatcher(
                binding.editTextPrice,
                BrazilianMoneyFormatter()
            )
        )

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val quantity = binding.editTextQuantity.text.toString().toIntOrNull() ?: 0
            val price = binding.editTextPrice.text.toString().parseBrazilianCurrency()
            val checked = binding.alreadyBoughtCheckbox.isChecked
            val shoppingItem = ShoppingItem(
                name = name,
                quantity = quantity,
                price = price,
                isChecked = checked
            )
            onSave(shoppingItem, shoppingList)
            dialog.dismiss()
        }

        return dialog
    }

    private fun onDecreaseQuantity() {
        val currentQuantity = binding.editTextQuantity.text.toString().toIntOrNull() ?: 0
        if (currentQuantity > 0) {
            val newQuantity = (currentQuantity - 1).toString()
            binding.editTextQuantity.setText(newQuantity)
        }
    }

    private fun onIncreaseQuantity() {
        val currentQuantity = binding.editTextQuantity.text.toString().toIntOrNull() ?: 0
        val newQuantity = (currentQuantity + 1).toString()
        binding.editTextQuantity.setText(newQuantity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
