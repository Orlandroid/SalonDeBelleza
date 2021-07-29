package com.example.citassalon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarSucursal


class AgendarSucursal : AppCompatActivity() {

    private lateinit var listViewSucursales: ListView
    private lateinit var buttonNext: Button
    private lateinit var textAgendarSucursal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_sucursal)
        val data = arrayOf("Zahra Norte", "Zahra Centro", "Zahra Sur", "CDMX", "Polanco")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewSucursales = findViewById(R.id.list_view_agendar_sucursal)
        textAgendarSucursal = findViewById(R.id.text_agendar_sucursal)
        listViewSucursales.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                textAgendarSucursal.text = data[position]
            }
        buttonNext = findViewById(R.id.button_sigiente_sucursal)
        buttonNext.setOnClickListener {
            val intent = Intent(this, AgendarStaff::class.java)
            startActivity(intent)
        }
        listViewSucursales.adapter = adapter
    }

}