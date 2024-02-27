package com.example.taller1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

class Recomendaciones : AppCompatActivity() {

    private var favoritoAgregado: Boolean = false
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

        try {
            mostrarRecomendacion(nombreTV, paisTV, categoriaTV, planTV, precioTV, catMasFrecuente, arregloDestinos)
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar la recomendación", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarRecomendacion(nombreTV: TextView, paisTV: TextView, categoriaTV: TextView, planTV: TextView, precioTV: TextView, catMasFrecuente: String, arregloDestinos: JSONArray) {
        val textViewNA = findViewById<TextView>(R.id.NA)
        val textViewnombre = findViewById<TextView>(R.id.nombre)
        val textViewpais = findViewById<TextView>(R.id.pais)
        val textViewcategoria = findViewById<TextView>(R.id.categoria)
        val textViewplan = findViewById<TextView>(R.id.plan)
        val textViewprecio = findViewById<TextView>(R.id.precio)

        val botonFav = findViewById<Button>(R.id.añadirFavoritos)

        if (catMasFrecuente == "NA") {
            textViewNA.visibility = View.VISIBLE
            textViewnombre.visibility = View.GONE
            textViewpais.visibility = View.GONE
            textViewcategoria.visibility = View.GONE
            textViewplan.visibility = View.GONE
            textViewprecio.visibility = View.GONE

            botonFav.setOnClickListener {
                Toast.makeText(this, "No hay recomendaciones disponibles", Toast.LENGTH_SHORT).show()
            }
        } else {
            textViewNA.visibility = View.GONE
            textViewnombre.visibility = View.VISIBLE
            textViewpais.visibility = View.VISIBLE
            textViewcategoria.visibility = View.VISIBLE
            textViewplan.visibility = View.VISIBLE
            textViewprecio.visibility = View.VISIBLE

            val destinosFavoritos = DestinoFavorito.Favoritos.favoritos.map { it.nombre }

            val destinosDisponibles = mutableListOf<JSONObject>()
            for (i in 0 until arregloDestinos.length()) {
                val destino = arregloDestinos.getJSONObject(i)
                if (!destinosFavoritos.contains(destino.getString("nombre"))) {
                    destinosDisponibles.add(destino)
                }
            }
            if (destinosDisponibles.isNotEmpty()) {
                val destinoAleatorio = destinoAleatorio(JSONArray(destinosDisponibles), catMasFrecuente)

                if (destinoAleatorio != null) {
                    nombreTV.text = destinoAleatorio.getString("nombre")
                    paisTV.text = destinoAleatorio.getString("pais")
                    categoriaTV.text = destinoAleatorio.getString("categoria")
                    planTV.text = destinoAleatorio.getString("plan")
                    precioTV.text = "USD " + destinoAleatorio.getString("precio")

                    botonFav.setOnClickListener {
                        if (!favoritoAgregado && DestinoFavorito.Favoritos.favoritos.none { it.nombre == destinoAleatorio.getString("nombre") }) {
                            val dest = destinoDesdeJSONObject(destinoAleatorio)
                            DestinoFavorito.Favoritos.favoritos.add(dest)
                            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                            favoritoAgregado = true
                        } else {
                            Toast.makeText(this, "Este destino ya está en tus favoritos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "No hay destinos disponibles", Toast.LENGTH_SHORT).show()
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

    fun destinoAleatorio(destinos: JSONArray, categoria: String): JSONObject? {

        val destinosPorCategoria = mutableListOf<JSONObject>()

        for (i in 0 until destinos.length()) {
            val d = destinos.getJSONObject(i)
            if (d.getString("categoria") == categoria)
                destinosPorCategoria.add(d)

        }
        val destinoAleatorio = destinosPorCategoria[Random.nextInt(destinosPorCategoria.size)]

        if (destinosPorCategoria.isEmpty()){
            return null
        }
        return destinoAleatorio
    }

}fun destinoDesdeJSONObject(json: JSONObject): Destino {
    return Destino(
        json.getString("nombre"),
        json.getString("pais"),
        json.getString("categoria"),
        json.getString("plan"),
        "USD " + json.getString("precio")
    )
}



