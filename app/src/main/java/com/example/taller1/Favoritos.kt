package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class Favoritos : AppCompatActivity() {
    lateinit var arregloFav: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritoss)

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        BTNVolver.setOnClickListener{
            val intentMenu = Intent(this, MainActivity::class.java)
            startActivity(intentMenu)
        }
        val listaFav = findViewById<ListView>(R.id.listaFav)
        arregloFav = mutableListOf()

        val favs = MainActivity.favoritos;

        for(f in favs){

        }
    }
}