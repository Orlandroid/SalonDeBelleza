package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class AgendarServicio : AppCompatActivity() {

    private lateinit var listViewServicios: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_servicio)
        val data = arrayOf("Corte de cabelo", "Aplicaciones de tines", "Tratamiento capilr")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewServicios = findViewById(R.id.list_view_servicios)
        listViewServicios.adapter = adapter
    }
}