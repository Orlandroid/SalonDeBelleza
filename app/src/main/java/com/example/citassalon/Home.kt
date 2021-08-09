package com.example.citassalon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Home : Fragment() {

    private lateinit var buttonAgendar: Button
    private lateinit var buttonPerfil: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        buttonAgendar = view.findViewById(R.id.button_agendar)
        buttonPerfil = view.findViewById(R.id.btnFloatingPerfil)
        buttonAgendar.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_agendarSucursal)
        }
        buttonPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_perfil)
        }
        return view
    }

}