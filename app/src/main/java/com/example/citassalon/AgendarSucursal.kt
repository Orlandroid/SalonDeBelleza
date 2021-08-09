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
import com.google.android.material.bottomnavigation.BottomNavigationView


class AgendarSucursal : Fragment() {


    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sucural: TextView
    private lateinit var recyclerViewSucursal: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_sucursal, container, false)
        sucural = view.findViewById(R.id.text_agendar_sucursal)
        bottomNavigationView = view.findViewById(R.id.sucursal_bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_back -> {
                    findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                }
                R.id.item_home -> {
                    findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                }
                R.id.item_next -> {
                    findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
                }
            }
            true
        }
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
        return view
    }

}