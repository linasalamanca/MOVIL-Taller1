package com.example.taller1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class FavoritosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val listaFavoritos: ListView = findViewById(R.id.listaFavoritos)

        val destinosSeleccionados = obtenerDestinosSeleccionados()

        val adaptador: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            destinosSeleccionados.map { it.nombre }
        )

        listaFavoritos.adapter = adaptador
    }

    private fun obtenerDestinosSeleccionados(): List<Destino> {
        try {
            val json = leerArchivoJson()
            val jsonArray = JSONArray(json)

            val destinos = mutableListOf<Destino>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val nombre = jsonObject.getString("nombre")
                val descripcion = jsonObject.getString("descripcion")
                val categoria = jsonObject.getString("categoria")

                // Modifica aquí para filtrar solo los destinos con la categoría "Favoritos"
                if (categoria == "Favoritos") {
                    destinos.add(Destino(nombre, descripcion))
                }
            }

            return destinos
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    private fun leerArchivoJson(): String {
        val inputStream = assets.open("destino.json")
        return inputStream.bufferedReader().use { it.readText() }
    }
}
