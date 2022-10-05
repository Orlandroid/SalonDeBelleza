package com.example.citassalon.ui.info.establecimiento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentEstablecimientoBinding
import com.example.citassalon.ui.extensions.navigate


class EstablecimientoFragment : Fragment() {

    private var _binding: FragmentEstablecimientoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstablecimientoBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Nombre establecimeinto"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            sucursales.cardMenu.setOnClickListener {
                val action = EstablecimientoFragmentDirections.actionEstablecimientoToSucursales2()
                navigate(action)
            }
            productos.cardMenu.setOnClickListener {
                val action =
                    EstablecimientoFragmentDirections.actionEstablecimientoToListOfProductsFragment()
                navigate(action)
            }
        }
        setMenuName()
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