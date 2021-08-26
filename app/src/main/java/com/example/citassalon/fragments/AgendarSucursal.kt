package com.example.citassalon.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.adapters.AdaptadorAgendarSucursal
import com.example.citassalon.databinding.FragmentAgendarSucursalBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AgendarSucursal : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {


    private var _binding: FragmentAgendarSucursalBinding? = null
    private val binding get() = _binding!!


    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarSucursalBinding.inflate(inflater)
        binding.sucursalBottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.recyclerSucursal.adapter =
            AdaptadorAgendarSucursal(
                arrayListOf(
                    "Zahra Norte",
                    "Zahra Centro",
                    "Zahra Oriente",
                    "Mexico",
                    "Guadalajara",
                    "Zacatecas",
                    "Aguascalientes",
                    "Queretaro"
                ), binding.textAgendarSucursal
            )
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarSucursal_to_agendarStaff)
                true
            }
            else -> false
        }
    }

}