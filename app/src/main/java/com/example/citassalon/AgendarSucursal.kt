package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarSucursal


class AgendarSucursal : AppCompatActivity() {

    private lateinit var listViewSucursales: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_sucursal)
        val data = arrayOf("Zahra Norte", "Zahra Centro", "Zahra Sur", "CDMX", "Polanco")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewSucursales = findViewById(R.id.list_view_agendar_sucursal)
        listViewSucursales.adapter = adapter
    }

}