package com.example.citassalon.ui.info.sucursal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNegocioInfoBinding
import com.example.citassalon.ui.info.establecimiento.EstablecimientoDirections
import com.example.citassalon.util.INFO_NEGOCIO_TO_INFO_SERVICIOS
import com.example.citassalon.util.INFO_NEGOCIO_TO_NUESTRO_STAFF
import com.example.citassalon.util.INFO_NEGOCIO_TO_UBICACION
import com.example.citassalon.util.navigate


class NegocioInfoFragment : Fragment() {

    private var _binding: FragmentNegocioInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNegocioInfoBinding.inflate(layoutInflater, container, false)
        setMenuName()
        binding.menuSttaf.cardMenu.setOnClickListener {
            val action = NegocioInfoFragmentDirections.actionNegocioInfoToNuestroStaff()
            navigate(action)
        }
        binding.menuServicios.cardMenu.setOnClickListener {
            val action = NegocioInfoFragmentDirections.actionNegocioInfoToInfoServicios()
            navigate(action)
        }
        binding.menuUbicacion.cardMenu.setOnClickListener {
            val action = NegocioInfoFragmentDirections.actionNegocioInfoToUbicacion()
            navigate(action)
        }
        return binding.root
    }

    private fun setMenuName() {
        binding.menuSttaf.textElement.text = context?.getString(R.string.staff)
        binding.menuServicios.textElement.text = context?.getString(R.string.servicios)
        binding.menuUbicacion.textElement.text = context?.getString(R.string.ubicacion)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}