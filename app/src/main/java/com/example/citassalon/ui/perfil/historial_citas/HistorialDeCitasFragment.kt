package com.example.citassalon.ui.perfil.historial_citas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.mappers.toAppointmentObject
import com.example.citassalon.data.mappers.toAppointmentResponse
import com.example.citassalon.data.models.remote.Appointment
import com.example.citassalon.data.models.remote.AppointmentResponse
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.ui.extensions.gone
import com.example.citassalon.ui.extensions.invisible
import com.example.citassalon.ui.extensions.visible
import com.example.citassalon.util.AlertsDialogMessages
import com.example.citassalon.util.SwipeRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistorialDeCitasFragment : Fragment(), ClickOnItem<Appointment>,
    SwipeRecycler.SwipeRecyclerListenr {

    private var _binding: FragmentHistorialDeCitasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistorialCitasViewModel by viewModels()
    private val swipeRecycler = SwipeRecycler()
    private val historialCitasAdapter = HistorialCitasAdapter(getListener())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialDeCitasBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObservers()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Historial de citas"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            progressBar2.gone()
            swipeRecycler.swipe(binding.recyclerAppointment, getListenerSwipeRecyclerListenr())
        }
    }


    private fun getListener(): ClickOnItem<Appointment> = this

    private fun getListenerSwipeRecyclerListenr() = this

    private fun setUpObservers() {
        viewModel.appointment.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.progressBar2.visible()
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.progressBar2.invisible()
                        binding.recyclerAppointment.adapter = historialCitasAdapter
                        historialCitasAdapter.setData(it.data)
                    }
                }
                is ApiState.Error -> {
                    binding.progressBar2.invisible()
                }
                is ApiState.ErrorNetwork -> {
                    binding.progressBar2.invisible()
                }
                is ApiState.NoData -> {
                    with(binding) {
                        progressBar2.invisible()
                        imageNoData.visible()
                        imageNoData.setAnimation(getRandomNoDataAnimation())
                        imageNoData.playAnimation()
                    }
                }
            }
        }
        viewModel.removeAppointment.observe(viewLifecycleOwner) {
            binding.progressBar2.isVisible = it is ApiState.Loading
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clikOnElement(element: Appointment, position: Int?) {
        val appointment = element.toAppointmentObject()
        val action = HistorialDeCitasFragmentDirections.actionHistorialDeCitasToHistorialDetailFragment(appointment)
        findNavController().navigate(action)
    }

    override fun onMove() {

    }

    override fun onSwipe(position: Int) {
        val alert = AlertDialogs(
            AlertDialogs.WARNING_MESSAGE,
            "Estas seguro que deseas eliminar el registro",
            object : AlertDialogs.ClickOnAccept {
                override fun clikOnAccept() {
                    val appointment = historialCitasAdapter.getElement(position)
                    binding.progressBar2.visible()
                    viewModel.removeAppointment(appointment.idAppointment)
                }

                override fun clikOnCancel() {
                    historialCitasAdapter.notifyDataSetChanged()
                }
            }, isTwoButtonDialog = true
        )
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

}