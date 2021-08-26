package com.example.citassalon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentCitaAgendadaBinding


class CitaAgendada : Fragment() {

    private var _binding: FragmentCitaAgendadaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitaAgendadaBinding.inflate(inflater, container, false)
        binding.buttonAceptar.setOnClickListener {
            findNavController().navigate(R.id.action_citaAgendada_to_home3)
        }
        return binding.root
    }

}