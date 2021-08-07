package com.example.citassalon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class CitaAgendada : Fragment() {

    private lateinit var buttonAceptar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cita_agendada, container, false)
        buttonAceptar = view.findViewById(R.id.button_aceptar)
        buttonAceptar.setOnClickListener {
            findNavController().navigate(R.id.action_citaAgendada_to_home3)
        }
        return view

    }

}