package com.example.citassalon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarServicio : Fragment() {

    private lateinit var buttonNext: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_servicio, container, false)
        buttonNext = view.findViewById(R.id.button_next_servicio)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarServicio_to_agendarFecha)
        }
        return view
    }
}