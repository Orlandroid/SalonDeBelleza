package com.example.citassalon.ui.perfil.historial_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHistorialDetailBinding
import com.example.citassalon.ui.base.BaseFragment


class HistorialDetailFragment :
    BaseFragment<FragmentHistorialDetailBinding>(R.layout.fragment_historial_detail) {

    private val args:HistorialDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi() {
        val appointment = args.appointment
        with(binding) {
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbarLayout.toolbarTitle.text="Historial"
            with(appointment){
                tvEstablecimiento.text="Establecimiento: $establecimeinto"
                tvEmpleado.text="Empleado: $empleado"
                tvServicio.text="Servicio: $servicio"
                tvHora.text=hora
                tvFecha.text=fecha
            }
        }
    }

}