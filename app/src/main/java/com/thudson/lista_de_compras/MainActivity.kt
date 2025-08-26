package com.thudson.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.thudson.lista_de_compras.databinding.ActivityMainBinding
import com.thudson.lista_de_compras.database.AppDatabase
import com.thudson.lista_de_compras.database.entity.ShoppingItem
import com.thudson.lista_de_compras.database.entity.ShoppingList
import com.thudson.lista_de_compras.domain.ShoppingListViewModel
import com.thudson.lista_de_compras.domain.ShoppingViewModelFactory
import com.thudson.lista_de_compras.ui.adapter.ListAdapter
import com.thudson.lista_de_compras.utils.ToastUtil
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureViewModel()
        configureAdapter()
        binding.addListButton.setOnClickListener { onAddList() }
        binding.toolbarMenuIcon.setOnClickListener { onMenuIconClick() }
    }

    private fun configureAdapter() {
        adapter = ListAdapter(
            onMoreOptionsClick = { view, item -> showPopupMenu(view, item) },
            onListClick = { item ->
                val intent = Intent(this, ListItemsActivity::class.java).apply {
                    putExtra("listId", item.id)
                }
                startActivity(intent)
            }
        )
        binding.listsRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.listsRecyclerview.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lists.collect { lists ->
                    adapter.updateData(lists)
                }
            }
        }
    }

    private fun showPopupMenu(view: View, item: ShoppingList) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.options_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_share -> {
                    val message = buildString {
                        append("${item.name}\n\n")
                        item.items.forEach { shoppingItem ->
                            append("- ${shoppingItem.quantity} ${shoppingItem.name}\n")
                        }
                    }
                    shareText(message)
                    true
                }
                R.id.action_edit -> {
                    EditListDialog(item) { newName, oldList ->
                        val updated = oldList?.copy(name = newName.capitalize())
                            ?: ShoppingList(name = newName.capitalize())
                        viewModel.updateList(updated)
                    }.show(supportFragmentManager, "EditListDialog")
                    ToastUtil.showShort(this, "Lista editada")
                    true
                }
                R.id.action_duplicate -> {
                    viewModel.addList(item.duplicate())
                    true
                }
                R.id.action_delete -> {
                    viewModel.deleteList(item)
                    ToastUtil.showShort(this, "Lista deletada")
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Compartilhar lista"))
    }

    private fun configureViewModel() {
        val dao = AppDatabase.getDatabase(applicationContext).shoppingListDao()
        viewModel = ViewModelProvider(
            this,
            ShoppingViewModelFactory(dao)
        )[ShoppingListViewModel::class.java]
    }

    private fun onMenuIconClick() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun onAddList() {
        EditListDialog(null) { name, _ ->
            viewModel.addList(ShoppingList(name = name.capitalize(), items = listOf(ShoppingItem(name = "Arroz"), ShoppingItem(name = "Feijão"))))
            ToastUtil.showShort(this, "Lista criada")
        }.show(supportFragmentManager, "AddListDialog")
    }

}