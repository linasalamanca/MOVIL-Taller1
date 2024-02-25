package com.example.taller1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class FavoritosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        setContentView(R.layout.activity_favoritos)

        val listaFavoritos: ListView = findViewById(R.id.listaFavoritos)

        val destinosSeleccionados = obtenerDestinosSeleccionados()

        val adaptador: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            destinosSeleccionados.map { it.nombre }
        )

        listaFavoritos.adapter = adaptador

        /*val intMenu = Intent(this, MainActivity::class.java)
        BTNVolver.setOnClickListener{startActivity(intMenu)}*/

    }

    private fun obtenerDestinosSeleccionados(): List<Destino> {
        try {
            val json = loadJSONFromAsset()
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


    fun loadJSONFromAsset(): JSONArray {
        var json: String? = null
        try {
            val istream: InputStream = assets.open("destinos.json")
            val size: Int = istream.available()
            val buffer = ByteArray(size)
            istream.read(buffer)
            istream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()

        }
        val jsonObject = JSONObject(json)
        return jsonObject.getJSONArray("destinos")
    }
}
