package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarConfirmacion : Fragment() {


    private lateinit var buttonNext: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_confirmacion, container, false)
        buttonNext = view.findViewById(R.id.button_sigiente)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarConfirmacion_to_citaAgendada)
        }
        return view
    }

}