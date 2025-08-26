package com.thudson.lista_de_compras.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thudson.lista_de_compras.database.dao.ShoppingListDao
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import com.thudson.lista_de_compras.database.entity.ShoppingList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.find

class ShoppingListViewModel(private val dao: ShoppingListDao) : ViewModel() {

    val lists: StateFlow<List<ShoppingList>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getById(id: String) = viewModelScope.launch {
        dao.getById(id)
    }

    fun addList(list: ShoppingList) = viewModelScope.launch {
        dao.insert(list)
    }

    fun updateList(list: ShoppingList) = viewModelScope.launch {
        dao.update(list)
    }

    fun deleteList(list: ShoppingList) = viewModelScope.launch {
        dao.delete(list)
    }

    fun addItem(listId: String, newItem: ShoppingItem) = viewModelScope.launch {
        val list = dao.getById(listId) ?: return@launch
        val updatedList = list.copy(items = list.items + newItem)
        dao.update(updatedList)
    }


}
