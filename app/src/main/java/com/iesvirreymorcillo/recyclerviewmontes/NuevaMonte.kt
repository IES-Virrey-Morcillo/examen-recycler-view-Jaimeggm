package com.iesvirreymorcillo.recyclerviewmontes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iesvirreymorcillo.recyclerviewmontes.databinding.ActivityNuevaMonteBinding

class NuevaMonte : AppCompatActivity() {

    private lateinit var binding: ActivityNuevaMonteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNuevaMonteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGuardarMonte.setOnClickListener {
            val nombre = binding.etNombreMonte.text.toString()
            val foto = binding.etFotoMonte.text.toString()
            val altura = binding.etAlturaMonte.text.toString()
            val continente = binding.etContinenteMonte.text.toString()

            if (nombre.isNotEmpty() && foto.isNotEmpty() && altura.isNotEmpty() && continente.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("nombre", nombre)
                    putExtra("foto", foto)
                    putExtra("altura", altura)
                    putExtra("continente", continente)
                }

                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelarMonte.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
