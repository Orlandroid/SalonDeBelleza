package com.example.citassalon.ui.servicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.citassalon.R
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgendarServicioBinding.inflate(inflater, container, false)
        binding.servicioBottomNavigationView.setOnNavigationItemSelectedListener(this)
        setUpObservers()
        return binding.root
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
                findNavController().navigate(R.id.action_agendarServicio_to_agendarStaff)
                true
            }
            R.id.item_home -> {
                findNavController().navigate(R.id.action_agendarServicio_to_home3)
                true
            }
            R.id.item_next -> {
                findNavController().navigate(R.id.action_agendarServicio_to_agendarFecha)
                true
            }
            else -> false
        }
    }

    override fun clickOnServicio(servicio: String) {
        binding.tvServicio.text = servicio
    }


}