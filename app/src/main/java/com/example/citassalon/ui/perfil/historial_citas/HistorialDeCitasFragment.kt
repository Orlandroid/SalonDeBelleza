package com.example.citassalon.ui.perfil.historial_citas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toAppointmentObject
import com.example.citassalon.data.models.remote.Appointment
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.domain.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.base.BaseFragment
import com.example.citassalon.ui.extensions.visible
import com.example.citassalon.util.SwipeRecycler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialDeCitasFragment :
    BaseFragment<FragmentHistorialDeCitasBinding>(R.layout.fragment_historial_de_citas),
    ClickOnItem<Appointment>,
    SwipeRecycler.SwipeRecyclerListenr {

    private val viewModel: HistorialCitasViewModel by viewModels()
    private val swipeRecycler = SwipeRecycler()
    private val historialCitasAdapter = HistorialCitasAdapter(getListener())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Historial de citas"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            swipeRecycler.swipe(binding.recyclerAppointment, getListenerSwipeRecyclerListenr())
        }
    }


    private fun getListener(): ClickOnItem<Appointment> = this

    private fun getListenerSwipeRecyclerListenr() = this


    @SuppressLint("SetTextI18n")
    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.appointment.observe(viewLifecycleOwner) {
            showAndHideProgress(it)
            when (it) {
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.toolbarLayout.tvInfo.apply {
                            visible()
                            text = "Total: ${it.data.size}"
                        }
                        binding.recyclerAppointment.adapter = historialCitasAdapter
                        historialCitasAdapter.setData(it.data)
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
                is ApiState.NoData -> {
                    with(binding) {
                        imageNoData.visible()
                        imageNoData.setAnimation(getRandomNoDataAnimation())
                        imageNoData.playAnimation()
                    }
                }
            }
        }
        viewModel.removeAppointment.observe(viewLifecycleOwner) {
            showAndHideProgress(it)
            when (it) {
                is ApiState.Success -> {
                    val alert = AlertDialogs(
                        kindOfMessage = AlertDialogs.SUCCES_MESSAGE,
                        "Appointment eliminado"
                    )
                    activity?.supportFragmentManager?.let { it1 -> alert.show(it1, "Dialog") }
                }
                is ApiState.Error -> {
                    val alert = AlertDialogs(
                        kindOfMessage = AlertDialogs.SUCCES_MESSAGE,
                        "Error al Eliminar el appointment"
                    )
                    activity?.supportFragmentManager?.let { it1 -> alert.show(it1, "Dialog") }
                }
                is ApiState.ErrorNetwork -> {
                    val alert = AlertDialogs(
                        kindOfMessage = AlertDialogs.SUCCES_MESSAGE,
                        "Verifica tu conexion de internet"
                    )
                    activity?.supportFragmentManager?.let { it1 -> alert.show(it1, "Dialog") }
                }
                is ApiState.NoData -> {

                }
                else -> {}
            }
        }
    }


    private fun getRandomNoDataAnimation(): Int =
        when ((1..3).random()) {
            1 -> {
                R.raw.no_data_animation
            }
            2 -> {
                R.raw.no_data_available
            }

            else -> R.raw.no_data_found
        }


    override fun clikOnElement(element: Appointment, position: Int?) {
        val appointment = element.toAppointmentObject()
        val action =
            HistorialDeCitasFragmentDirections.actionHistorialDeCitasToHistorialDetailFragment(
                appointment
            )
        findNavController().navigate(action)
    }

    override fun onMove() {

    }

    override fun onSwipe(position: Int) {
        val alert = AlertDialogs(
            AlertDialogs.WARNING_MESSAGE,
            "Estas seguro que deseas eliminar el registro",
            object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
                    val appointment = historialCitasAdapter.getElement(position)
                    viewModel.removeAppointment(appointment.idAppointment)
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun clickOnCancel() {
                    historialCitasAdapter.notifyDataSetChanged()
                }
            }, isTwoButtonDialog = true
        )
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

}