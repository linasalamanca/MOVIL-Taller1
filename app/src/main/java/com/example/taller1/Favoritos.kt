package com.example.taller1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Favoritos : AppCompatActivity() {
    lateinit var listaFavoritos: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        BTNVolver.setOnClickListener {
            val intentMenu = Intent(this, MainActivity::class.java)
            startActivity(intentMenu)
        }
        val listaFav = findViewById<ListView>(R.id.listaFav)
        val favs = DestinoFavorito.Favoritos.favoritos
        listaFavoritos = mutableListOf()

        for (f in favs) {
            listaFavoritos.add(f.nombre)
        }

        val adaptadorLista = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFavoritos)
        listaFav.adapter = adaptadorLista

        val textViewNA = findViewById<TextView>(R.id.NA)

        if (listaFavoritos.isEmpty()) {
            textViewNA.visibility = View.VISIBLE
        } else {
            textViewNA.visibility = View.GONE
        }

       /* listaFav.setOnItemClickListener { _, _, position, _ ->
            val destinoSeleccionado = listaFavoritos[position]

            val intent = Intent(this@Favoritos, SeleccionadoFavoritosActivity::class.java)
            intent.putExtra("destinoSeleccionado", destinoSeleccionado)
            startActivity(intent)
        }*/
    }
}