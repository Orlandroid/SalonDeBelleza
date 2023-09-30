package com.example.citassalon.presentacion.ui.perfil.historial_citas

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.presentacion.interfaces.ClickOnItem
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.ui.MainActivity
import com.example.citassalon.presentacion.ui.base.BaseFragment
import com.example.citassalon.presentacion.ui.extensions.gone
import com.example.citassalon.presentacion.ui.extensions.observeApiResultGeneric
import com.example.citassalon.presentacion.ui.extensions.showSuccessMessage
import com.example.citassalon.presentacion.ui.extensions.visible
import com.example.citassalon.presentacion.util.SwipeRecycler
import com.example.domain.entities.remote.firebase.AppointmentFirebase
import com.example.domain.mappers.toAppointmentObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialDeCitasFragment :
    BaseFragment<FragmentHistorialDeCitasBinding>(R.layout.fragment_historial_de_citas),
    ClickOnItem<AppointmentFirebase>, SwipeRecycler.SwipeRecyclerListenr {

    private val viewModel: HistorialCitasViewModel by viewModels()
    private val swipeRecycler = SwipeRecycler()
    private val historialCitasAdapter = HistorialCitasAdapter(getListener())

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.historiasl_de_citas)
    )


    override fun setUpUi() {
        swipeRecycler.swipe(binding.recyclerAppointment, getListenerSwipeRecyclerListenr())

    }


    private fun getListener(): ClickOnItem<AppointmentFirebase> = this

    private fun getListenerSwipeRecyclerListenr() = this


    @SuppressLint("SetTextI18n")
    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(liveData = viewModel.appointment,
            onLoading = { binding.progressBar.visible() },
            onFinishLoading = { binding.progressBar.gone() },
            noData = {
                with(binding) {
                    imageNoData.visible()
                    imageNoData.setAnimation(getRandomNoDataAnimation())
                    imageNoData.playAnimation()
                }
            }) {
            // Add this code with the generic toolbar
            /*
            binding.toolbarLayout.tvInfo.apply {
                visible()
                text = "Total: ${it.size}"
            }*/
            binding.recyclerAppointment.adapter = historialCitasAdapter
            historialCitasAdapter.setData(it)
        }
        observeApiResultGeneric(
            liveData = viewModel.removeAppointment,
            hasProgressTheView = true,
        ) {
            showSuccessMessage("Appointment eliminado")
        }
    }


    private fun getRandomNoDataAnimation(): Int = when ((1..3).random()) {
        1 -> {
            R.raw.no_data_animation
        }

        2 -> {
            R.raw.no_data_available
        }

        else -> R.raw.no_data_found
    }


    override fun clikOnElement(element: AppointmentFirebase, position: Int?) {
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
            },
            isTwoButtonDialog = true
        )
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

}