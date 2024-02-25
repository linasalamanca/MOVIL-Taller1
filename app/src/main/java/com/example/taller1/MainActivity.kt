package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var seleccionCategoria: String
    private var favoritoAgregado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BTNDestinos = findViewById<Button>(R.id.BotonExplorarDestinos)
        val BTNFavoritos = findViewById<Button>(R.id.BotonFavoritos)
        val BTNRecomendaciones = findViewById<Button>(R.id.BotonRecomendaciones)
        val spinnerCategorias = findViewById<Spinner>(R.id.Spinner_tipo_destino)
        val btnIrAFavoritos = findViewById<Button>(R.id.btnIrAFavoritos)

        seleccionCategoria = "no funciono"
        spinnerCategorias.onItemSelectedListener = this

        val intExplorarDestinos = Intent(this, ExplorarDestinos::class.java)
        val bundleExplorarDestinos = Bundle()
        bundleExplorarDestinos.putString("categoria", seleccionCategoria)
        intExplorarDestinos.putExtra("infoCategoria", bundleExplorarDestinos)

        val intRecomendaciones = Intent(this, Recomendaciones::class.java)
        val intFavoritos = Intent(this, FavoritosActivity::class.java)

        BTNDestinos.setOnClickListener { startActivity(intExplorarDestinos) }

        BTNRecomendaciones.setOnClickListener { startActivity(intRecomendaciones) }

        BTNFavoritos.setOnClickListener {
            if (!favoritoAgregado && seleccionCategoria != "no funciono") {
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                favoritoAgregado = true
            } else if (seleccionCategoria == "no funciono") {
                Toast.makeText(this, "Por favor, selecciona una categoría primero", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Este destino ya está en tus favoritos", Toast.LENGTH_SHORT).show()
            }
        }

        btnIrAFavoritos.setOnClickListener {
            val intentFavoritos = Intent(this, FavoritosActivity::class.java)
            startActivity(intentFavoritos)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        seleccionCategoria = parent.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(baseContext, "Por favor, selecciona una categoría", Toast.LENGTH_SHORT).show()
    }

    fun loadJSONFromAsset(): String? {
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
            return null
        }
        return json
    }
}