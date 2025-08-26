package com.thudson.lista_de_compras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.thudson.lista_de_compras.databinding.DialogEditListBinding
import com.thudson.lista_de_compras.database.entity.ShoppingList
import com.thudson.lista_de_compras.databinding.DialogEditListItemBinding

class EditListItemDialog(
    private val shoppingList: ShoppingList?,
    private val onSave: (String, ShoppingList?) -> Unit
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
        binding.decreaseQuantity.setOnClickListener { onDecreaseQuantity() }
        binding.increaseQuantity.setOnClickListener { onIncreaseQuantity() }

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            if (name.isNotEmpty()) {
                onSave(name, shoppingList)
                dialog.dismiss()
            } else {
                binding.editTextName.error = "Informe o nome do item"
            }
        }

        return dialog
    }

    private fun onDecreaseQuantity() {
        val currentQuantity = binding.editTextQuantity.text.toString().toIntOrNull() ?: 0
        if (currentQuantity > 0) {
            val newQuantity = (currentQuantity - 1).toString()
            binding.editTextName.setText(newQuantity)
        }
    }

    private fun onIncreaseQuantity() {
        val currentQuantity = binding.editTextQuantity.text.toString().toIntOrNull() ?: 0
        val newQuantity = (currentQuantity + 1).toString()
        binding.editTextName.setText(newQuantity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
