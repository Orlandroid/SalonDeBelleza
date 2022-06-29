package com.example.citassalon.ui.info.sucursal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentNegocioInfoBinding
import com.example.citassalon.ui.staff.AgendarStaffArgs
import com.example.citassalon.util.navigate


class NegocioInfoFragment : Fragment() {

    private var _binding: FragmentNegocioInfoBinding? = null
    private val binding get() = _binding!!
    private val args: NegocioInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNegocioInfoBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
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
                val action = NegocioInfoFragmentDirections.actionNegocioInfoToUbicacion(args.currentSucursal)
                navigate(action)
            }
        }
        setMenuName()
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