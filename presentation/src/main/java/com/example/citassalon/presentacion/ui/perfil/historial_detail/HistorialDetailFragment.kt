package com.example.citassalon.presentacion.ui.perfil.historial_detail

import android.annotation.SuppressLint
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHistorialDetailBinding
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.domain.entities.local.AppointmentObject


class HistorialDetailFragment :
    BaseFragment<FragmentHistorialDetailBinding>(R.layout.fragment_historial_detail) {

    private val args: HistorialDetailFragmentArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "Historial"
    )


    @SuppressLint("SetTextI18n")
    override fun setUpUi() {
        val appointment: AppointmentObject = args.appointment
        with(binding) {
            tvEstablecimiento.text = "Establecimiento: ${appointment.establishment}"
            tvEmpleado.text = "Empleado: ${appointment.employee}"
            tvServicio.text = "Servicio: ${appointment.service}"
            tvHora.text = "Hora: ${appointment.hour}"
            tvFecha.text = "Fecha: ${appointment.date}"
        }
    }


}