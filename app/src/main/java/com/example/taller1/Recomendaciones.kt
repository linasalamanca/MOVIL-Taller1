package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONArray
import kotlin.random.Random

class Recomendaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendaciones)

        val nombreTV = findViewById<TextView>(R.id.nombre)
        val paisTV = findViewById<TextView>(R.id.pais)
        val categoriaTV = findViewById<TextView>(R.id.categoria)
        val planTV = findViewById<TextView>(R.id.plan)
        val precioTV = findViewById<TextView>(R.id.precio)
       // val destinos = JSONArray(intent.getStringExtra("destinos"))
        val destinoMasFrecuente = masFrecuente()

        nombreTV.text = destinoMasFrecuente.nombre
        paisTV.text = destinoMasFrecuente.descripcion
        categoriaTV.text = destinoMasFrecuente.categoria
        planTV.text = destinoMasFrecuente.plan
        precioTV.text = destinoMasFrecuente.precio
    }

    fun masFrecuente() : Destino
    {
        val destinos = FavoritosActivity.FavoritosSingleton.destinos
        if(destinos.isEmpty())
            return Destino("NA","NA","NA", "NA", "NA")
        var categoriaMasFrecuente:String = ""
        val frecuenciaCategorias = mutableMapOf<String, Int>()
        for (d in FavoritosActivity.FavoritosSingleton.destinos)
        {
            val categoria = d.categoria
            val contadorFrecuencia = frecuenciaCategorias.getOrDefault(categoria,0)
            frecuenciaCategorias[categoria] = contadorFrecuencia + 1;
        }
        categoriaMasFrecuente = frecuenciaCategorias.maxBy { it.value }.key

        val destinosMasFrecuentes = destinos.filter { it.categoria == categoriaMasFrecuente }
        val destinoAleatorio = Random.nextInt(destinosMasFrecuentes.size)
        return destinosMasFrecuentes[destinoAleatorio]
    }
}


