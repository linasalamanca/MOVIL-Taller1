package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ExplorarDestinos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar_destinos)

        val texticoPorfi = findViewById<TextView>(R.id.textico)

        val informacion = intent.getBundleExtra("info")

        texticoPorfi.text = informacion?.getString("categoria")
    }
}