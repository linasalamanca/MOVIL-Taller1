package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DestinoFavorito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino_favorito)

        val nombre = findViewById<TextView>(R.id.nombreFav)
        val pais = findViewById<TextView>(R.id.paisFav)
        val categoria = findViewById<TextView>(R.id.categoriaFav)
        val plan = findViewById<TextView>(R.id.planFav)
        val plata = findViewById<TextView>(R.id.precioFav)
        val botonFav = findViewById<Button>(R.id.añadirFavoritos)
    }
}