package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class Favoritos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        val BTNVolver = findViewById<Button>(R.id.btnVolver)
        BTNVolver.setOnClickListener{
            val intentMenu = Intent(this, MainActivity::class.java)
            startActivity(intentMenu)
        }

    }
}