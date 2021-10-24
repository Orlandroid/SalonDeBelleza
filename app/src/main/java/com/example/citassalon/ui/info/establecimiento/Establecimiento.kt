package com.example.citassalon.ui.info.establecimiento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentEstablecimientoBinding
import com.example.citassalon.util.ESTABLECIMIENTO_TO_SUCURSALES
import com.example.citassalon.util.navigate


class Establecimiento : Fragment() {

    private var _binding: FragmentEstablecimientoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstablecimientoBinding.inflate(inflater, container, false)
        setMenuName()
        binding.sucursales.cardMenu.setOnClickListener {
            it.navigate(ESTABLECIMIENTO_TO_SUCURSALES)
        }
        binding.productos.cardMenu.setOnClickListener {
            it.navigate(R.id.action_establecimiento_to_sucursales2)
        }
        return binding.root
    }

    private fun setMenuName() {
        binding.sucursales.textElement.text = context?.getString(R.string.sucursales)
        binding.productos.textElement.text = context?.getString(R.string.productos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}