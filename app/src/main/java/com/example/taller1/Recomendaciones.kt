package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Recomendaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val nombreTV = findViewById<TextView>(R.id.nombre)
        val paisTV = findViewById<TextView>(R.id.pais)
        val categoriaTV = findViewById<TextView>(R.id.categoria)
        val planTV = findViewById<TextView>(R.id.plan)
        val precioTV = findViewById<TextView>(R.id.precio)
        val categoriaMasFrecuente = masFrecuente()
    }

    fun masFrecuente() : String
    {
        var categoriaMasFrecuente:String = ""
        val frecuenciaCategorias = mutableMapOf<String, Int>()
        return categoriaMasFrecuente
    }
}