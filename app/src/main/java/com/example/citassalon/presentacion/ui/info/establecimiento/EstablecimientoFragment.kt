package com.example.citassalon.presentacion.ui.info.establecimiento

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentEstablecimientoBinding
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.navigate


class EstablecimientoFragment :
    BaseFragment<FragmentEstablecimientoBinding>(R.layout.fragment_establecimiento) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
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
        with(binding) {
            sucursales.textElement.text = context?.getString(R.string.sucursales)
            productos.textElement.text = context?.getString(R.string.productos)
        }
    }

}