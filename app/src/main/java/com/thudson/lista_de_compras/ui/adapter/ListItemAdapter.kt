package com.thudson.lista_de_compras.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import com.thudson.lista_de_compras.databinding.ListItemBinding
import com.thudson.lista_de_compras.toCurrency

class ListItemAdapter(
    private val onCheckboxClick: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    private var items: List<ShoppingItem> = emptyList()

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItem) {
            binding.listItemName.text = item.name
            binding.listItemPrice.text = item.price.toCurrency()
            binding.listItemCheckbox.isChecked = item.isChecked
            binding.listItemCheckbox.setOnClickListener { _ -> onCheckboxClick(item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<ShoppingItem>) {
        items = newItems
        notifyDataSetChanged()
    }

}