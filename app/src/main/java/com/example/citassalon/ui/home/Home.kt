package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHomeBinding


class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.buttonAgendar.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_agendarSucursal)
        }
        binding.btnFloatingPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_perfil)
        }
        binding.btnFloatingList.setOnClickListener {
            findNavController().navigate(R.id.action_home3_to_sucursales)
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}