package com.thudson.lista_de_compras.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thudson.lista_de_compras.databinding.ListBinding
import com.thudson.lista_de_compras.database.entity.ShoppingList

class ListAdapter(
    private val onMoreOptionsClick: (View, ShoppingList) -> Unit,
    private val onListClick: (ShoppingList) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var items: List<ShoppingList> = emptyList()

    inner class ViewHolder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingList) {
            binding.listName.text = item.name
            val listItems = "${item.items.size} itens"
            binding.listItems.text = listItems

            binding.listMoreOptionsIcon.setOnClickListener { view -> onMoreOptionsClick(view, item) }
            binding.listContainer.setOnClickListener { _ -> onListClick(item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListBinding.inflate(
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
    fun updateData(newItems: List<ShoppingList>) {
        items = newItems
        notifyDataSetChanged()
    }

}