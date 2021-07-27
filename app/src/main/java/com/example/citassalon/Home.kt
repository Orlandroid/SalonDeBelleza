package com.example.citassalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Home : AppCompatActivity() {
    private lateinit var btnAgendar: Button
    private lateinit var text: TextView
    private lateinit var btnFloatingPerfil : Button
    private lateinit var btnFloatingList : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Toast.makeText(this, "Entrando a la primera activida", Toast.LENGTH_SHORT).show()

        btnAgendar = findViewById(R.id.btnAgendar)
        text = findViewById(R.id.text)
        btnAgendar.setOnClickListener {
            btnAgendar.isEnabled = false
            text.visibility = View.INVISIBLE

            btnFloatingPerfil = findViewById(R.id.btnFloatingPerfil)
            btnFloatingList = findViewById(R.id.btnFloatingList)
            val intent = Intent(this, AgendarSucursal::class.java)
            startActivity(intent)
    }
    }}