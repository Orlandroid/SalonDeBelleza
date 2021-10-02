package com.example.citassalon.ui.confirmarcita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarConfirmacionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarConfirmacion : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentAgendarConfirmacionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarConfirmacionBinding.inflate(inflater, container, false)
        binding.confirmacionBottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.buttonConfirmar.setOnClickListener {
            findNavController().navigate(R.id.action_agendarConfirmacion_to_citaAgendada)
        }
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.element_back -> {
                findNavController().navigate(R.id.action_agendarConfirmacion_to_agendarFecha)
                true
            }
            R.id.element_home -> {
                findNavController().navigate(R.id.action_agendarConfirmacion_to_home3)
                true
            }
            else -> false
        }
    }

}