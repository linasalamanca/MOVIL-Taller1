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

    object FavoritosSingleton{
        var destinos : MutableList<Destino> = mutableListOf()
    }
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

        FavoritosSingleton.destinos = destinosSeleccionados.toMutableList()

        listaFavoritos.adapter = adaptador

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        BTNVolver.setOnClickListener{
            val intentMenu = Intent(this, MainActivity::class.java)
            startActivity(intentMenu)
        }
    }



    private fun obtenerDestinosSeleccionados(): List<Destino> {
        try {
            val json = leerArchivoJson()
            val jsonArray = JSONArray(json)

            val destinos = mutableListOf<Destino>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val nombre = jsonObject.getString("nombre")
                val pais = jsonObject.getString("pais")
                val categoria = jsonObject.getString("categoria")
                val plan = jsonObject.getString("plan")
                val precio = jsonObject.getString("precio")

                if (categoria == "Favoritos") {
                    val nombre = jsonObject.getString("nombre")
                    val destinoEncontrado = MainActivity.favoritos.find { it.nombre == nombre }
                    if(destinoEncontrado != null)
                        destinos.add(destinoEncontrado)
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
