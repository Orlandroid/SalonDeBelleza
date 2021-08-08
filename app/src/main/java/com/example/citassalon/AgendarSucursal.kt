package com.example.citassalon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.citassalon.adapters.AdaptadorAgendarSucursal


class AgendarSucursal : Fragment() {


    private lateinit var buttonNext: Button
    private lateinit var sucural: TextView
    private lateinit var recyclerViewSucursal: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_sucursal, container, false)
        buttonNext = view.findViewById(R.id.button_next_servicio)
        sucural = view.findViewById(R.id.text_agendar_sucursal)
        recyclerViewSucursal = view.findViewById(R.id.recycler_agendar_sucursal)
        recyclerViewSucursal.adapter =
            AdaptadorAgendarSucursal(
                arrayListOf(
                    "Zahra Norte",
                    "Zahra Centro",
                    "Zahra Oriente",
                    "Mexico",
                    "Guadalajara",
                    "Zacatecas",
                    "Aguascalientes",
                    "Queretaro"
                ), sucural
            )
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
            Toast.makeText(requireContext(), "Sigiente", Toast.LENGTH_SHORT).show()
        }
        return view
    }

}