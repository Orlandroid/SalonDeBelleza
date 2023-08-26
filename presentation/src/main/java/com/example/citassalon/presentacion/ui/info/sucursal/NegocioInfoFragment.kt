package com.example.citassalon.presentacion.ui.info.sucursal

import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNegocioInfoBinding
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.click
import com.example.citassalon.presentacion.ui.extensions.navigate

class NegocioInfoFragment :
    BaseFragment<FragmentNegocioInfoBinding>(R.layout.fragment_negocio_info) {

    private val args: NegocioInfoFragmentArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Sucursal"
    )

    override fun setUpUi() {
        with(binding) {
            menuSttaf.cardMenu.click {
                val action = NegocioInfoFragmentDirections.actionNegocioInfoToNuestroStaff()
                navigate(action)
            }
            menuServicios.cardMenu.click {
                val action = NegocioInfoFragmentDirections.actionNegocioInfoToInfoServicios()
                navigate(action)
            }
            menuUbicacion.cardMenu.click {
                val action =
                    NegocioInfoFragmentDirections.actionNegocioInfoToUbicacion(args.currentSucursal)
                navigate(action)
            }
        }
        setMenuName()
    }

    private fun setMenuName() {
        with(binding) {
            menuSttaf.textElement.text = context?.getString(R.string.staff)
            menuServicios.textElement.text = context?.getString(R.string.servicios)
            menuUbicacion.textElement.text = context?.getString(R.string.ubicacion)
        }
    }

}