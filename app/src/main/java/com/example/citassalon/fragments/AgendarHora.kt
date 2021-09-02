package com.example.citassalon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarHoraBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarHora : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentAgendarHoraBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarHoraBinding.inflate(inflater, container, false)
        binding.bottonNavigationViewAgendarHora.setOnNavigationItemSelectedListener(this)
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarHora_to_agendarFecha)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarHora_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarHora_to_agendarConfirmacion)
                true
            }
            else -> false
        }
    }


}