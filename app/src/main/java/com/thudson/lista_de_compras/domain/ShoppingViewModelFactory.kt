package com.thudson.lista_de_compras.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thudson.lista_de_compras.database.dao.ShoppingListDao

class ShoppingViewModelFactory(private val dao: ShoppingListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingListViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
