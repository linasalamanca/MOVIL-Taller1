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

    fun destinoAleatorio(destinos: JSONArray, categoria: String): JSONObject? {

        val destinosPorCategoria = mutableListOf<JSONObject>()
        if (destinosPorCategoria.isEmpty())
            return null

        for (i in 0 until destinos.length()) {
            val d = destinos.getJSONObject(i)
            if (d.getString("categoria") == categoria)
                destinosPorCategoria.add(d)

        }
        val destinoAleatorio = destinosPorCategoria[Random.nextInt(destinosPorCategoria.size)]

        return destinoAleatorio
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
                nombreTV.text = destinoAleatorio.getString("nombre")
                paisTV.text = destinoAleatorio.getString("pais")
                categoriaTV.text = destinoAleatorio.getString("categoria")
                planTV.text = destinoAleatorio.getString("plan")
                precioTV.text = "USD " + destinoAleatorio.getString("precio")
            }
        }
    }
}


