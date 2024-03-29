package com.example.citassalon.presentacion.features.info.establecimiento

import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentEstablecimientoBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.click
import com.example.citassalon.presentacion.features.extensions.navigate


class EstablecimientoFragment :
    BaseFragment<FragmentEstablecimientoBinding>(R.layout.fragment_establecimiento) {
    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.nombre_establecimiento)
    )

    override fun setUpUi() {
        with(binding) {
            sucursales.cardMenu.click {
                navigate(EstablecimientoFragmentDirections.actionEstablecimientoToSucursales2())
            }
            stores.cardMenu.click {
                navigate(EstablecimientoFragmentDirections.actionEstablecimientoToStoresFragment())
            }
        }
        setMenuName()
    }

    private fun setMenuName() {
        with(binding) {
            sucursales.textElement.text = getString(R.string.sucursales)
            stores.textElement.text = getString(R.string.tiendas)
        }
    }

}