package com.example.citassalon.presentacion.features.perfil.historial_detail

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHistorialDetailBinding
import com.example.citassalon.presentacion.features.MainActivity
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.fromJson
import com.example.domain.entities.local.AppointmentObject


class HistorialDetailFragment :
    BaseFragment<FragmentHistorialDetailBinding>(R.layout.fragment_historial_detail) {

    private val args: HistorialDetailFragmentArgs by navArgs()
    lateinit var appointment: AppointmentObject

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Historial"
    )

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {
        appointment = args.appointment.fromJson()
        with(binding) {
            tvEstablecimiento.text = "Establecimiento: ${appointment.establishment}"
            tvEmpleado.text = "Empleado: ${appointment.employee}"
            tvServicio.text = "Servicio: ${appointment.service}"
            tvHora.text = "Hora: ${appointment.hour}"
            tvFecha.text = "Fecha: ${appointment.date}"
        }
    }

}