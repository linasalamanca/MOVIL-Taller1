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
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var seleccionCategoria: String
    companion object{
        var favoritos: MutableList<Destino> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BTNDestinos = findViewById<Button>(R.id.BotonExplorarDestinos)
        val BTNRecomendaciones = findViewById<Button>(R.id.BotonRecomendaciones)
        val spinnerCategorias = findViewById<Spinner>(R.id.Spinner_tipo_destino)
        val btnIrAFavoritos = findViewById<Button>(R.id.btnIrAFavoritos)

        seleccionCategoria = "no funciono"
        spinnerCategorias.onItemSelectedListener = this

        BTNDestinos.setOnClickListener { explorarDestinos() }

        BTNRecomendaciones.setOnClickListener { recomendaciones() }

        btnIrAFavoritos.setOnClickListener {irAFavoritos()}
    }

    private fun explorarDestinos()
    {
        val intExplorarDestinos = Intent(this, ExplorarDestinos::class.java)
        val bundleExplorarDestinos = Bundle()
        bundleExplorarDestinos.putString("categoria", seleccionCategoria)
        intExplorarDestinos.putExtra("infoCategoria", bundleExplorarDestinos)
        startActivity(intExplorarDestinos)
    }

    private fun recomendaciones()
    {
        val destinos = loadJSONFromAsset()
        val intRecomendaciones = Intent(this, Recomendaciones::class.java).apply {
            putExtra("destinos", destinos.toString())
        }
        startActivity(intRecomendaciones)
    }
    private fun irAFavoritos()
    {
        val intentFavoritos = Intent(this, Favoritos::class.java)
        startActivity(intentFavoritos)
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        seleccionCategoria = parent.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(baseContext, "Por favor, selecciona una categoría", Toast.LENGTH_SHORT).show()
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