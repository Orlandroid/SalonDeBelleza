package com.example.citassalon.ui.perfil.historial_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.databinding.FragmentHistorialDetailBinding
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.cita_agendada.CitaAgendadaFragment
import com.example.citassalon.ui.cita_agendada.CitaAgendadaFragment.Companion.HISTORIAL


class HistorialDetailFragment :
    BaseFragment<FragmentHistorialDetailBinding>(R.layout.fragment_historial_detail) {

    private val args:HistorialDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi() {
        val appointment: Appointment? = if (args.appointment != null){
            args.appointment
        }else{
            activity?.intent?.getParcelableExtra(HISTORIAL)
        }
        with(binding) {
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbarLayout.toolbarTitle.text="Historial"
            with(appointment){
                tvEstablecimiento.text="Establecimiento: ${this?.establecimeinto}"
                tvEmpleado.text="Empleado: ${this?.empleado}"
                tvServicio.text="Servicio: ${this?.servicio}"
                tvHora.text= this?.hora ?: ""
                tvFecha.text = this?.fecha
            }
        }
    }

    fun getApponitmentByBundle(){

    }

}