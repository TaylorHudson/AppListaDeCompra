package com.thudson.lista_de_compras

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.thudson.lista_de_compras.database.AppDatabase
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import com.thudson.lista_de_compras.database.entity.ShoppingList
import com.thudson.lista_de_compras.databinding.ActivityListItemsBinding
import com.thudson.lista_de_compras.databinding.ActivityMainBinding
import com.thudson.lista_de_compras.databinding.ListItemBinding
import com.thudson.lista_de_compras.domain.ShoppingListViewModel
import com.thudson.lista_de_compras.domain.ShoppingViewModelFactory
import com.thudson.lista_de_compras.ui.adapter.ListAdapter
import com.thudson.lista_de_compras.ui.adapter.ListItemAdapter
import com.thudson.lista_de_compras.utils.ToastUtil
import kotlinx.coroutines.launch

class ListItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListItemsBinding
    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var adapter: ListItemAdapter
    private var listId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listId = intent.getStringExtra("listId")

        enableEdgeToEdge()

        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureViewModel()
        configureAdapter()

        binding.toolbarBackButton.setOnClickListener { onBackButtonClick() }
        binding.addListItemButton.setOnClickListener { onAddListItemClick() }
    }

    private fun onAddListItemClick() {
        EditListItemDialog(null) { item, list ->
            viewModel.addItem(list!!.id, item)
            ToastUtil.showShort(this, "Item criado")
        }.show(supportFragmentManager, "AddListItemDialog")
    }

    private fun onBackButtonClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun configureViewModel() {
        val dao = AppDatabase.getDatabase(applicationContext).shoppingListDao()
        viewModel = ViewModelProvider(
            this,
            ShoppingViewModelFactory(dao)
        )[ShoppingListViewModel::class.java]
    }

    private fun configureAdapter() {
        adapter = ListItemAdapter { item -> }
        binding.listItemsRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.listItemsRecyclerview.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lists.collect { lists ->
                    val list = lists.find { it.id == listId }
                    list?.let {
                        adapter.updateData(it.items)
                    }
                }
            }
        }
    }
}