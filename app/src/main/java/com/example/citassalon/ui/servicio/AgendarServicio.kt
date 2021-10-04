package com.example.citassalon.ui.servicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citassalon.R
import com.example.citassalon.data.models.Servicio
import com.example.citassalon.databinding.FragmentAgendarServicioBinding
import com.example.citassalon.util.ApiState
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgendarServicio : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    ListernerClickOnService {


    private var _binding: FragmentAgendarServicioBinding? = null
    private val binding get() = _binding!!
    private val viewModelAgendarServicio: ViewModelAgendarServicio by viewModels()


    private val args: AgendarServicioArgs by navArgs()
    private var currentServicio: Servicio? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarServicioBinding.inflate(inflater, container, false)
        binding.servicioBottomNavigationView.setOnNavigationItemSelectedListener(this)
        setUpObservers()
        setValuesToView(args)
        return binding.root
    }

    private fun setValuesToView(args: AgendarServicioArgs) {
        binding.sucursal.text = args.sucursal
        binding.staffImage.setImageResource(args.staff.getResourceImage())
        binding.nombreStaff.text = args.staff.nombre
    }

    private fun setUpObservers() {
        viewModelAgendarServicio.serviceLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ApiState.Loading -> {
                    binding.progressBarS.visibility = View.VISIBLE
                }
                is ApiState.Success -> {
                    if (it.data != null) {
                        binding.progressBarS.visibility = View.GONE
                        binding.recyclerAgendarServicio.adapter = AdaptadorServicio(it.data, this)
                    }
                }
                is ApiState.Error -> {
                    binding.progressBarS.visibility = View.GONE
                }
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_back -> {
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarServicio_to_home3)
                true
            }
            R.id.item_next -> {
                val action = currentServicio?.let {
                    AgendarServicioDirections.actionAgendarServicioToAgendarFecha(
                        args.sucursal,
                        args.staff,
                        it
                    )
                }
                action?.let { findNavController().navigate(it) }
                true
            }
            else -> false
        }
    }

    override fun clickOnServicio(servicio: Servicio) {
        binding.tvServicio.text = servicio.name
        currentServicio = servicio
    }


}