package com.example.citassalon.ui.info.sucursal

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNegocioInfoBinding
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.navigate

class NegocioInfoFragment :
    BaseFragment<FragmentNegocioInfoBinding>(R.layout.fragment_negocio_info) {

    private val args: NegocioInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Sucursal"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            menuSttaf.cardMenu.setOnClickListener {
                val action = NegocioInfoFragmentDirections.actionNegocioInfoToNuestroStaff()
                navigate(action)
            }
            menuServicios.cardMenu.setOnClickListener {
                val action = NegocioInfoFragmentDirections.actionNegocioInfoToInfoServicios()
                navigate(action)
            }
            menuUbicacion.cardMenu.setOnClickListener {
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