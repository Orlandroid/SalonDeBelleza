package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarServicio
import com.example.citassalon.adapters.AdaptadorAgendarSucursal
import com.example.citassalon.models.Servicio

class AgendarServicio : Fragment() {

    private lateinit var buttonNext: Button
    private lateinit var servicio: TextView
    private lateinit var recyclerViewServicio: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_servicio, container, false)
        buttonNext = view.findViewById(R.id.button_next_servicio)
        recyclerViewServicio = view.findViewById(R.id.recycler_agendar_servicio)
        recyclerViewServicio.adapter =
            AdaptadorAgendarServicio(
                arrayListOf(
                    Servicio("Corte de cabello"),
                    Servicio("Aplicación de tinte"),
                    Servicio("Tratamiento Capilar"),
                    Servicio("Depilación"),
                    Servicio("Alaciado"),
                    Servicio("Corte de uñas"),
                    Servicio("Limpieza facial"),
                    Servicio("Maquillaje"),
                    Servicio("Manicure"),
                    Servicio("Pedicure")
                ))
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarServicio_to_agendarFecha)
        }
        return view
    }


}