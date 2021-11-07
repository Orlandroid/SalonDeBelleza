package com.example.citassalon.ui.perfil.historial_citas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citassalon.databinding.FragmentHistorialDeCitasBinding
import com.example.citassalon.util.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialDeCitas : Fragment() {

    private var _binding: FragmentHistorialDeCitasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelHistorialCitas by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorialDeCitasBinding.inflate(layoutInflater, container, false)
        setUpObservers()
        return binding.root
    }


    private fun setUpObservers() {
        viewModel.appointment.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Loading -> {

                }
                is ApiState.Success -> {
                    Log.w("DATOS", it.data.toString())
                    if (it.data != null) {
                        binding.recyclerAppointment.adapter = AdaptadorHistorialCitas(it.data)
                    }
                }
                is ApiState.Error -> {

                }
                is ApiState.ErrorNetwork -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}