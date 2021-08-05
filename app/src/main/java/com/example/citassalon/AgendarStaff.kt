package com.example.citassalon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AgendarStaff : Fragment() {

    private lateinit var buttonSigiente: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_staff, container, false)
        buttonSigiente = view.findViewById(R.id.button_sigiente)
        buttonSigiente.setOnClickListener {
            findNavController().navigate(R.id.action_agendarStaff_to_agendarServicio)
        }
        return view
    }

}