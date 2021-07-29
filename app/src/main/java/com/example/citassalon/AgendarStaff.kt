package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AgendarStaff : AppCompatActivity() {
    private lateinit var btnSiguiente: Button
    private lateinit var text: TextView
    private lateinit var btnFloating1: FloatingActionButton
    private lateinit var btnFloating2: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_staff)
        Toast.makeText(this, "Entrando a la tercera actividad", Toast.LENGTH_SHORT).show()

        btnSiguiente = findViewById(R.id.btnSiguiente)

        btnSiguiente.setOnClickListener {
            btnSiguiente.isEnabled = false
            text.visibility = View.INVISIBLE


        }
    }
}