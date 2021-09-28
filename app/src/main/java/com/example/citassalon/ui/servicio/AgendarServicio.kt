package com.example.citassalon.ui.servicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentAgendarServicioBinding
import com.example.citassalon.data.models.Servicio
import com.google.android.material.bottomnavigation.BottomNavigationView

class AgendarServicio : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    ListernerClickOnService {


    private var _binding: FragmentAgendarServicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarServicioBinding.inflate(inflater, container, false)
        binding.servicioBottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.recyclerAgendarServicio.adapter =
            AdaptadorServicio(
                arrayListOf(
                    Servicio("Corte de cabello", 100),
                    Servicio("Aplicación de tinte", 100),
                    Servicio("Tratamiento Capilar", 100),
                    Servicio("Depilación", 100),
                    Servicio("Alaciado", 100),
                    Servicio("Corte de uñas", 100),
                    Servicio("Limpieza facial", 100),
                    Servicio("Maquillaje", 100),
                    Servicio("Manicure", 100),
                    Servicio("Pedicure", 100)
                ), this
            )
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                findNavController().navigate(R.id.action_agendarServicio_to_agendarStaff)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarServicio_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarServicio_to_agendarFecha)
                true
            }
            else -> false
        }
    }

    override fun clickOnServicio(servicio: String) {
        binding.tvServicio.text = servicio
    }


}