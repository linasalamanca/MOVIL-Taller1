package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var seleccionCategoria:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BTNDestinos = findViewById<Button>(R.id.BotonExplorarDestinos)
        val BTNFavoritos = findViewById<Button>(R.id.BotonFavoritos)
        val BTNRecomendaciones = findViewById<Button>(R.id.BotonRecomendaciones)
        val spinnerCategorias = findViewById<Spinner>(R.id.Spinner_tipo_destino)
        val destinos = loadJSONFromAsset()

        seleccionCategoria = "no funciono"
        spinnerCategorias.onItemSelectedListener = this
        Log.i("MIRAME",seleccionCategoria)

        val intExplorarDestinos = Intent(this, ExplorarDestinos::class.java)
        val bundleExplorarDestinos = Bundle()
        bundleExplorarDestinos.putString("categoria", seleccionCategoria)
        intExplorarDestinos.putExtra("infoCategoria",bundleExplorarDestinos)

        val intRecomendaciones = Intent(this, Recomendaciones::class.java)
        val intFavoritos = Intent(this, Favoritos::class.java)

        BTNDestinos.setOnClickListener{ startActivity(intExplorarDestinos) }

        BTNRecomendaciones.setOnClickListener{ startActivity(intRecomendaciones) }

        BTNFavoritos.setOnClickListener{ startActivity(intFavoritos)}
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        seleccionCategoria = parent.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(baseContext, "Por favor, selecciona una categoría", Toast.LENGTH_SHORT).show()
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try{
            val istream: InputStream = assets. open( "destinos.json")
            val size: Int = istream.available()
            val buffer = ByteArray(size)
            istream. read (buffer)
            istream.close()
            json = String(buffer, Charsets. UTF_8)
        }catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
