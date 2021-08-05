package com.example.citassalon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarFecha : Fragment() {

    private lateinit var buttonNext: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_fecha, container, false)
        buttonNext = view.findViewById(R.id.button_next)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarFecha_to_agendarHora)
        }
        return view
    }


}