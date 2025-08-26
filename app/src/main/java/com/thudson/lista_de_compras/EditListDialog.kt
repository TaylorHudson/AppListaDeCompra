package com.thudson.lista_de_compras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.thudson.lista_de_compras.databinding.DialogEditListBinding
import com.thudson.lista_de_compras.database.entity.ShoppingList

class EditListDialog(
    private val shoppingList: ShoppingList?,
    private val onSave: (String, ShoppingList?) -> Unit
) : DialogFragment() {

    private var listId: String? = null
    private var _binding: DialogEditListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listId = arguments?.getString("list_id")
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditListBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext()).setView(binding.root)
        val dialog = builder.create()

        shoppingList?.let {
            binding.editTextName.setText(it.name)
        }

        binding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            if (name.isNotEmpty()) {
                onSave(name, shoppingList)
                dialog.dismiss()
            } else {
                binding.editTextName.error = "Informe o nome"
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
