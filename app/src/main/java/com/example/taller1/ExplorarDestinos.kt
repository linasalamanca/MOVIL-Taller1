package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ExplorarDestinos : AppCompatActivity() {

    lateinit var arregloPaises: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar_destinos)

        val textCategoria = findViewById<TextView>(R.id.categoria)
        val informacion = intent.getBundleExtra("infoCategoria")
        val categoria = informacion?.getString("categoria")
        textCategoria.text = categoria

        val listaDestinos = findViewById<ListView>(R.id.listaDestinos)
        arregloPaises = mutableListOf()

        val datosJson = JSONObject(loadJSONFromAsset())
        val paisesJson = datosJson.getJSONArray("destinos")

        if(categoria == "Todos"){
            for(i in 0 until paisesJson.length()){
                val pais = paisesJson.getJSONObject(i)
                arregloPaises.add(pais.getString("nombre"))
            }
        }else{
            for(i in 0 until paisesJson.length()){
                val pais = paisesJson.getJSONObject(i)
                if(pais.getString("categoria") == categoria){
                    arregloPaises.add(pais.getString("nombre"))
                }
            }
        }

        val adaptadorLista = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPaises)
        listaDestinos.adapter = adaptadorLista

        listaDestinos.setOnItemClickListener{ parent, view, position, id ->
            val paisSelec = arregloPaises[position]
            val intentPais = Intent(this, DestinoFavorito::class.java)
            intentPais.putExtra("destinoSeleccionado", paisSelec.toString())
            startActivity(intentPais)
        }

    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try{
            val istream: InputStream = assets.open( "destinos.json")
            val size: Int = istream.available()
            val buffer = ByteArray(size)
            istream.read(buffer)
            istream.close()
            json = String(buffer, Charsets. UTF_8)
        }catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
