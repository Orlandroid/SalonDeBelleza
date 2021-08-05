package com.example.citassalon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class AgendarSucursal : Fragment() {


    private lateinit var buttonNext: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_sucursal, container, false)
        buttonNext = view.findViewById(R.id.button_next_servicio)
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
            Toast.makeText(requireContext(), "Sigiente", Toast.LENGTH_SHORT).show()
        }
        return view
    }


}