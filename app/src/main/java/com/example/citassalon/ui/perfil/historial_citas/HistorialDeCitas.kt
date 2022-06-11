package com.example.citassalon.ui.perfil.historial_citas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
import com.example.citassalon.data.models.Appointment
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.interfaces.ClickOnItem
import com.example.citassalon.main.AlertDialogs
import com.example.citassalon.util.SwipeRecycler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistorialDeCitas : Fragment(), ClickOnItem<Appointment>, SwipeRecycler.SwipeRecyclerListenr {

    private var _binding: FragmentHistorialDeCitasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelHistorialCitas by viewModels()
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

    private fun setUpUi() {
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Historial de citas"
            toolbarLayout.toolbarBack.setOnClickListener {
                findNavController().popBackStack()
            }
            progressBar2.visibility=View.GONE
            swipeRecycler.swipe(binding.recyclerAppointment, getListenerSwipeRecyclerListenr())
        }
    }


    private fun getListener(): ClickOnItem<Appointment> = this

    private fun getListenerSwipeRecyclerListenr() = this

    private fun setUpObservers() {
        viewModel.appointment.observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Loading -> {
                    binding.progressBar2.visibility=View.VISIBLE
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.progressBar2.visibility=View.INVISIBLE
                        binding.recyclerAppointment.adapter = historialCitasAdapter
                        historialCitasAdapter.setData(it.data)
                    }
                }
                is ApiState.Error -> {
                    binding.progressBar2.visibility=View.INVISIBLE
                }
                is ApiState.ErrorNetwork -> {
                    binding.progressBar2.visibility=View.INVISIBLE
                }
                is ApiState.NoData -> {
                    with(binding) {
                        binding.progressBar2.visibility=View.INVISIBLE
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

    override fun onMove() {

    }

    override fun onSwipe(position: Int) {
        val alert = AlertDialogs(
            AlertDialogs.WARNING_MESSAGE,
            "Estas seguro que deseas eliminar el registro",
            object : AlertDialogs.ClickOnAccept {
                override fun clikOnAccept() {
                    val appointment = historialCitasAdapter.getElement(position)
                    binding.progressBar2.visibility=View.VISIBLE
                    lifecycleScope.launch{
                        viewModel.removeAponintment(appointment)
                        viewModel.getAllAppointMents()
                    }
                }

                override fun clikOnCancel() {
                    historialCitasAdapter.notifyDataSetChanged()
                }
            }, isTwoButtonDialog = true)
        activity?.let { it1 -> alert.show(it1.supportFragmentManager, "dialog") }
    }

}