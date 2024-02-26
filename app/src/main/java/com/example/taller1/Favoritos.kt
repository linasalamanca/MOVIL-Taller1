package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class Favoritos : AppCompatActivity() {
    lateinit var listaFavoritos : MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritoss)

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        BTNVolver.setOnClickListener{
            val intentMenu = Intent(this, MainActivity::class.java)
            startActivity(intentMenu)
        }
        val listaFav = findViewById<ListView>(R.id.listaFav)


        val favs = DestinoFavorito.Favoritos.favoritos
        listaFavoritos = mutableListOf()

        for(f in favs){
            listaFavoritos.add(f.nombre)
        }

        val adaptadorLista = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFavoritos)
        listaFav.adapter = adaptadorLista
    }
}

