package com.thudson.lista_de_compras

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thudson.lista_de_compras.databinding.ActivityMainBinding
import com.thudson.lista_de_compras.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarBackIcon.setOnClickListener { onBackIconClick() }
        binding.exitButton.setOnClickListener { onExitClick() }
    }

    private fun onExitClick() {
        finishAffinity()
    }

    private fun onBackIconClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}