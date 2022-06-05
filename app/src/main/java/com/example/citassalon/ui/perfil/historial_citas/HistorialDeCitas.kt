package com.example.citassalon.ui.perfil.historial_citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialDeCitas : Fragment(), ClickOnItem<Appointment> {

    private var _binding: FragmentHistorialDeCitasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelHistorialCitas by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialDeCitasBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObservers()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Historial de citas"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    private fun getListener(): ClickOnItem<Appointment> = this

    private fun setUpObservers() {
        viewModel.appointment.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.recyclerAppointment.adapter =
                            HistorialCitasAdapter(it.data, getListener())
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
                is ApiState.NoData -> {
                    with(binding) {
                        imageNoData.visibility = View.VISIBLE
                        imageNoData.setAnimation(getRandomNoDataAnimation())
                        imageNoData.playAnimation()
                    }
                }
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
        val action =
            HistorialDeCitasDirections.actionHistorialDeCitasToHistorialDetailFragment(element)
        findNavController().navigate(action)
    }

}