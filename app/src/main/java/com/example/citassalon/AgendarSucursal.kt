package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarSucursal


class AgendarSucursal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_sucursal)
        Toast.makeText(this, "Entrando a la segunda activida", Toast.LENGTH_SHORT).show()
    }
}