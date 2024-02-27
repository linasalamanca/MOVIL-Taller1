package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL

class DestinoFavorito : AppCompatActivity() {

    private var favoritoAgregado = false
    private val client = OkHttpClient()
    object Favoritos {
        var favoritos: MutableList<Destino> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino_favorito)

        val destinos = loadJSONFromAsset()
        val nombre = findViewById<TextView>(R.id.nombreFav)
        val pais = findViewById<TextView>(R.id.paisFav)
        val categoria = findViewById<TextView>(R.id.categoriaFav)
        val plan = findViewById<TextView>(R.id.planFav)
        val plata = findViewById<TextView>(R.id.precioFav)
        val botonFav = findViewById<Button>(R.id.añadirFavoritos)
        val temperatura = findViewById<TextView>(R.id.tempFav)
        val nombreSeleccionado = intent.getStringExtra("destinoSeleccionado").toString()

        mostrarInfo(nombre, pais, categoria, plan, plata, nombreSeleccionado, destinos, botonFav, temperatura)

    }

    fun mostrarInfo(nombre:TextView, pais:TextView, categoria:TextView, plan:TextView, precio:TextView,nombreSeleccionado: String, destinos: JSONArray, botonFav:Button, temperatura:TextView)
        {
            val dest = obtenerInfo(nombreSeleccionado, destinos)
            nombre.text = dest.nombre
            pais.text = dest.precio
            categoria.text = dest.categoria
            plan.text = dest.plan
            precio.text = dest.precio

            botonFav.setOnClickListener {
                if (!favoritoAgregado && Favoritos.favoritos.none { it.nombre == dest.nombre}) {
                    Favoritos.favoritos.add(dest)
                    Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                    favoritoAgregado = true
                }  else {
                    Toast.makeText(this, "Este destino ya está en tus favoritos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    fun obtenerInfo(nombreSeleccionado: String, destinos: JSONArray): Destino {
            for (i in 0 until destinos.length()) {
                val destinoSeleccionado = destinos.getJSONObject(i)
                Log.i("P1", "Destino: ${destinoSeleccionado.getString("nombre")}")
                if (destinoSeleccionado.getString("nombre") == nombreSeleccionado) {

                    val nombre = destinoSeleccionado.getString("nombre")
                    val pais = destinoSeleccionado.getString("pais")
                    val categoria = destinoSeleccionado.getString("categoria")
                    val plan = destinoSeleccionado.getString("plan")
                    val precio = destinoSeleccionado.getInt("precio").toString()

                    return Destino(nombre, pais, categoria, plan, precio)
                }

            }
            return Destino("NA", "NA", "NA", "NA", "NA")
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
