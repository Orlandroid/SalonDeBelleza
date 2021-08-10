package com.example.citassalon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarConfirmacion : Fragment() {


    private lateinit var buttonConfirmacionCita: Button
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_confirmacion, container, false)
        buttonConfirmacionCita = view.findViewById(R.id.button_confirmacion_cita)
        bottomNavigationView = view.findViewById(R.id.confirmacion_bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.element_back -> {
                    findNavController().navigate(R.id.action_agendarConfirmacion_to_agendarHora)
                    true
                }
                R.id.element_home -> {
                    findNavController().navigate(R.id.action_agendarConfirmacion_to_home3)
                    true
                }
                else -> false
            }
        }
        buttonConfirmacionCita.setOnClickListener {
            findNavController().navigate(R.id.action_agendarConfirmacion_to_citaAgendada)
        }
        return view
    }

}