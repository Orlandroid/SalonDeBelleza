package com.example.citassalon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarConfirmacion : Fragment() {


    private lateinit var buttonConfirmacionCita: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_confirmacion, container, false)
        buttonConfirmacionCita = view.findViewById(R.id.button_confirmacion_cita)
        buttonConfirmacionCita.setOnClickListener {
            findNavController().navigate(R.id.action_agendarConfirmacion_to_citaAgendada)
        }
        return view
    }

}