package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.log
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
        val arregloDestinos = JSONArray(intent.getStringExtra("destinos"))
        val catMasFrecuente = categoriaMasFrecuente()

        mostrarRecomendacion(nombreTV, paisTV, categoriaTV, planTV, precioTV, catMasFrecuente, arregloDestinos)
    }

    private fun mostrarRecomendacion(nombreTV: TextView, paisTV: TextView, categoriaTV: TextView, planTV: TextView, precioTV: TextView, catMasFrecuente: String, arregloDestinos: JSONArray) {
        if (catMasFrecuente == "NA") {
            nombreTV.text = "NA"
            paisTV.text = "NA"
            categoriaTV.text = "NA"
            planTV.text = "NA"
            precioTV.text = "NA"
        } else {
            val destinoAleatorio = destinoAleatorio(arregloDestinos, catMasFrecuente)
            if (destinoAleatorio != null) {
                nombreTV.text = destinoAleatorio.nombre
                paisTV.text = destinoAleatorio.descripcion
                categoriaTV.text = destinoAleatorio.categoria
                planTV.text = destinoAleatorio.plan
                precioTV.text = destinoAleatorio.precio
            }
        }
    }

    fun categoriaMasFrecuente(): String {
        if (DestinoFavorito.Favoritos.favoritos.isEmpty()) {
            return "NA"
        }
        val frecuenciaCategorias = mutableMapOf<String, Int>()
        for (d in DestinoFavorito.Favoritos.favoritos) {
            val categoria = d.categoria
            val count = frecuenciaCategorias.getOrDefault(categoria, 0)

            frecuenciaCategorias[categoria] = count + 1
        }
        val categoriaMasFrecuente = frecuenciaCategorias.maxByOrNull { it.value }!!.key
        return categoriaMasFrecuente
    }

    fun destinoAleatorio(destinos: JSONArray, categoria: String): Destino? {

        val destinosPorCategoria = mutableListOf<Destino>()

      /*  for (i in 0 until DestinoFavorito.Favoritos.favoritos.size) {
            val d = destinos.getJSONObject(i)
            if (d.getString("categoria") == categoria)
                destinosPorCategoria.add(d)

        }*/
        for(d:Destino in DestinoFavorito.Favoritos.favoritos)
        {
            if(d.categoria == categoria)
            {
                destinosPorCategoria.add(d)
            }
        }
        val destinoAleatorio = destinosPorCategoria[Random.nextInt(destinosPorCategoria.size)]

        return destinoAleatorio

        if (destinosPorCategoria.isEmpty())
            return null
    }


}


