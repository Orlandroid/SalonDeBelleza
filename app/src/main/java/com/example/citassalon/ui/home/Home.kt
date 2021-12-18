package com.example.citassalon.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            buttonAgendar.setOnClickListener {
                navigate(HOME_TO_SUCURSALES)
            }
            btnFloatingPerfil.setOnClickListener {
                navigate(HOME_TO_PERFIL)
            }
            btnFloatingList.setOnClickListener {
                navigate(HOME_TO_NOMBRE_ESTABLECIMEINTO)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}