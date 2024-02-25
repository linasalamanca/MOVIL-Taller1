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

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var seleccionCategoria:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BTNDestinos = findViewById<Button>(R.id.BotonExplorarDestinos)
        val BTNFavoritos = findViewById<Button>(R.id.BotonFavoritos)
        val BTNRecomendaciones = findViewById<Button>(R.id.BotonRecomendaciones)
        val spinnerCategorias = findViewById<Spinner>(R.id.Spinner_tipo_destino)

        seleccionCategoria = "no funciono"
        spinnerCategorias.onItemSelectedListener = this
        Log.i("MIRAME",seleccionCategoria)

        val intExplorarDestinos = Intent(this, ExplorarDestinos::class.java)
        val bundleExplorarDestinos = Bundle()
        bundleExplorarDestinos.putString("categoria", seleccionCategoria)
        intExplorarDestinos.putExtra("infoCategoria",bundleExplorarDestinos)

        val intRecomendaciones = Intent(this, Recomendaciones::class.java)

        BTNDestinos.setOnClickListener{
            startActivity(intExplorarDestinos)
        }

        BTNRecomendaciones.setOnClickListener{
            startActivity(intRecomendaciones)
        }
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        seleccionCategoria = parent.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(baseContext, "Por favor, selecciona una categoría", Toast.LENGTH_SHORT).show()
    }
}
