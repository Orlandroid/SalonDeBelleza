package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.citassalon.databinding.FragmentHomeBinding
import com.example.citassalon.util.HOME_TO_NOMBRE_ESTABLECIMEINTO
import com.example.citassalon.util.HOME_TO_PERFIL
import com.example.citassalon.util.HOME_TO_SUCURSALES
import com.example.citassalon.util.navigate


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
            navigate(HOME_TO_SUCURSALES)
        }
        binding.btnFloatingPerfil.setOnClickListener {
            navigate(HOME_TO_PERFIL)
        }
        binding.btnFloatingList.setOnClickListener {
            navigate(HOME_TO_NOMBRE_ESTABLECIMEINTO)
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}